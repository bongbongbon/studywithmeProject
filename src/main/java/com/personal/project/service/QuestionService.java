package com.personal.project.service;

import com.personal.project.common.exception.CustomException;
import com.personal.project.common.exception.ErrorCode;
import com.personal.project.controller.question.dto.request.QuestionRequest;
import com.personal.project.controller.question.dto.response.QuestionResponse;
import com.personal.project.controller.reply.dto.request.ReplyRequest;
import com.personal.project.controller.reply.dto.response.ReplyResponse;
import com.personal.project.entity.*;
import com.personal.project.repository.*;
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
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public QuestionResponse createQuestion(UserDetails userDetails, Long teamId, QuestionRequest questionRequest) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        QuestionEntity question =  questionRepository.save(
                QuestionEntity.builder()
                        .content(questionRequest.getContent())
                        .user(user)
                        .team(team)
                        .build());

        return QuestionResponse.fromEntity(question);

    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> getTeamQuestions(Long teamId, Integer page, Integer pageSize) {

        return questionRepository.findAllByTeamIdOrderByCreatedAtDesc(teamId, PageRequest.of(page, pageSize))
                .stream()
                .map(QuestionResponse::fromEntity)
                .collect(Collectors.toList());
    }




    @Transactional
    public void deleteQuestion(UserDetails userDetails, Long replyId) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        QuestionEntity question = questionRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));

        // 작성자만 삭제 가능 로직
        if (!Objects.equals(user.getId(), question.getUser().getId())) {
            throw new CustomException(ErrorCode.USER_INFO_NOT_MATCH);
        }

        questionRepository.deleteById(replyId);
    }

    @Transactional
    public QuestionResponse updateQuestion(UserDetails userDetails, Long replyId, QuestionRequest questionRequest) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        QuestionEntity question = questionRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));

        // 작성자만 삭제 가능 로직
        if (!Objects.equals(user.getId(), question.getUser().getId())) {
            throw new CustomException(ErrorCode.USER_INFO_NOT_MATCH);
        }


        question.setContent(questionRequest.getContent());

        return QuestionResponse.fromEntity(questionRepository.save(question));
    }
}
