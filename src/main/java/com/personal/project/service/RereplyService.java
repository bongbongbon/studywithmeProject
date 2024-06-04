package com.personal.project.service;

import com.personal.project.common.exception.CustomException;
import com.personal.project.common.exception.ErrorCode;
import com.personal.project.controller.reply.dto.request.ReplyRequest;
import com.personal.project.controller.reply.dto.response.ReplyResponse;
import com.personal.project.controller.rereply.dto.request.RereplyRequest;
import com.personal.project.controller.rereply.dto.response.RereplyResponse;
import com.personal.project.entity.*;
import com.personal.project.repository.ReplyRepository;
import com.personal.project.repository.RereplyRepository;
import com.personal.project.repository.TeamRepository;
import com.personal.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RereplyService {

    private final RereplyRepository rereplyRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;

    // 답글 만들기
    @Transactional
    public RereplyResponse createRereply(UserDetails userDetails, Long replyId, RereplyRequest rereplyRequest) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ReplyEntity reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPLY_NOT_FOUND));

        RereplyEntity rereply =  rereplyRepository.save(
                RereplyEntity.builder()
                        .content(rereplyRequest.getContent())
                        .user(user)
                        .reply(reply)
                        .build());


        return RereplyResponse.fromEntity(rereply);

    }

    // 답글 리스트
    @Transactional
    public List<RereplyResponse> getRereplyList(Long replyId) {

        ReplyEntity reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPLY_NOT_FOUND));

        return rereplyRepository.findByReplyId(replyId)
                .stream()
                .map(RereplyEntity::toResponse)
                .collect(Collectors.toList());
    }


    // 답글 수정
    @Transactional
    public RereplyResponse updateRereply(UserDetails userDetails,Long replyId, Long rereplyId, RereplyRequest rereplyRequest) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ReplyEntity reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPLY_NOT_FOUND));

        RereplyEntity rereply = rereplyRepository.findById(rereplyId)
                .orElseThrow(() -> new CustomException(ErrorCode.RE_REPLY_NOT_FOUND));

        // 답글 입력한 사람만 수정가능
        if (!Objects.equals(user.getId(), rereply.getUser().getId())) {
            throw new CustomException(ErrorCode.USER_INFO_NOT_MATCH);
        }

        rereply.setContent(rereplyRequest.getContent());

        return RereplyResponse.fromEntity(rereplyRepository.save(rereply));

    }

    // 답글 삭제
    @Transactional
    public void deleteRereply(UserDetails userDetails, Long replyId, Long rereplyId) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ReplyEntity reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPLY_NOT_FOUND));

        RereplyEntity rereply = rereplyRepository.findById(rereplyId)
                .orElseThrow(() -> new CustomException(ErrorCode.RE_REPLY_NOT_FOUND));

        // 답글 입력한 사람만 삭제가능
        if (!Objects.equals(user.getId(), rereply.getUser().getId())) {
            throw new CustomException(ErrorCode.USER_INFO_NOT_MATCH);
        }

        rereplyRepository.deleteById(rereplyId);

    }


}
