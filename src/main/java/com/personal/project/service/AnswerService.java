package com.personal.project.service;

import com.personal.project.common.exception.CustomException;
import com.personal.project.common.exception.ErrorCode;
import com.personal.project.controller.answer.dto.request.AnswerRequest;
import com.personal.project.controller.answer.dto.response.AnswerResponse;
import com.personal.project.controller.rereply.dto.request.RereplyRequest;
import com.personal.project.controller.rereply.dto.response.RereplyResponse;
import com.personal.project.entity.*;
import com.personal.project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    // 답글 만들기
    @Transactional
    public AnswerResponse createAnswer(UserDetails userDetails, Long replyId, AnswerRequest answerRequest) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        QuestionEntity question = questionRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));

        AnswerEntity answer =  answerRepository.save(
                AnswerEntity.builder()
                        .content(answerRequest.getContent())
                        .user(user)
                        .question(question)
                        .build());


        return AnswerResponse.fromEntity(answer);
    }

    // 답글 리스트
    @Transactional
    public List<AnswerResponse> getAnswerList(Long questionId) {

        QuestionEntity question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));

        return answerRepository.findByQuestionId(questionId)
                .stream()
                .map(AnswerEntity::toResponse)
                .collect(Collectors.toList());
    }


    // 답글 수정
    @Transactional
    public AnswerResponse updateAnswer(UserDetails userDetails,Long questionId, Long answerId, AnswerRequest answerRequest) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        QuestionEntity question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));

        AnswerEntity answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new CustomException(ErrorCode.ANSWER_NOT_FOUND));

        // 답글 입력한 사람만 수정가능
        if (!Objects.equals(user.getId(), answer.getUser().getId())) {
            throw new CustomException(ErrorCode.USER_INFO_NOT_MATCH);
        }

        answer.setContent(answerRequest.getContent());

        return AnswerResponse.fromEntity(answerRepository.save(answer));

    }

    // 답글 삭제
    @Transactional
    public void deleteAnswer(UserDetails userDetails, Long questionId, Long answerId) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        QuestionEntity question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));

        AnswerEntity answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new CustomException(ErrorCode.ANSWER_NOT_FOUND));

        // 답글 입력한 사람만 삭제가능
        if (!Objects.equals(user.getId(), answer.getUser().getId())) {
            throw new CustomException(ErrorCode.USER_INFO_NOT_MATCH);
        }

        answerRepository.deleteById(answerId);

    }

}
