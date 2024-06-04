package com.personal.project.service;

import com.personal.project.common.exception.CustomException;
import com.personal.project.common.exception.ErrorCode;
import com.personal.project.common.response.ApiSuccessResponse;
import com.personal.project.controller.reply.dto.request.ReplyRequest;
import com.personal.project.controller.reply.dto.response.ReplyResponse;
import com.personal.project.entity.ReplyEntity;
import com.personal.project.entity.StudyBoardEntity;
import com.personal.project.entity.UserEntity;
import com.personal.project.repository.ReplyRepository;
import com.personal.project.repository.StudyBoardRepository;
import com.personal.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final StudyBoardRepository studyBoardRepository;

    @Transactional
    public ReplyResponse createReply(UserDetails userDetails, Long studyBoardId, ReplyRequest replyRequest) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        StudyBoardEntity studyBoard = studyBoardRepository.findById(studyBoardId)
                        .orElseThrow(() -> new CustomException(ErrorCode.STUDY_BOARD_NOT_FOUND));

        ReplyEntity reply =  replyRepository.save(
                ReplyEntity.builder()
                        .content(replyRequest.getContent())
                        .user(user)
                        .studyBoard(studyBoard)
                        .build());


        return ReplyResponse.fromEntity(reply);

    }

    @Transactional(readOnly = true)
    public List<ReplyResponse> getStudBoardReplies(Long studyBoardId, Integer page, Integer pageSize) {

        return replyRepository.findAllByStudyBoardIdOrderByCreatedAtDesc(studyBoardId, PageRequest.of(page, pageSize))
                .stream()
                .map(ReplyResponse::fromEntity)
                .collect(Collectors.toList());
    }




    @Transactional
    public void deleteReply(UserDetails userDetails, Long replyId) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ReplyEntity reply = replyRepository.findById(replyId)
                        .orElseThrow(() -> new CustomException(ErrorCode.REPLY_NOT_FOUND));

        // 작성자만 삭제 가능 로직
        if (!Objects.equals(user.getId(), reply.getUser().getId())) {
            throw new CustomException(ErrorCode.USER_INFO_NOT_MATCH);
        }

        replyRepository.deleteById(replyId);
    }

    @Transactional
    public ReplyResponse updateReply(UserDetails userDetails, Long replyId, ReplyRequest replyRequest) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ReplyEntity reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPLY_NOT_FOUND));

        // 작성자만 삭제 가능 로직
        if (!Objects.equals(user.getId(), reply.getUser().getId())) {
            throw new CustomException(ErrorCode.USER_INFO_NOT_MATCH);
        }


        reply.setContent(replyRequest.getContent());

        return ReplyResponse.fromEntity(replyRepository.save(reply));
    }
}
