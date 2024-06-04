package com.personal.project.controller.question;

import com.personal.project.common.response.ApiSuccessResponse;
import com.personal.project.controller.question.dto.request.QuestionRequest;
import com.personal.project.controller.question.dto.response.QuestionResponse;
import com.personal.project.controller.reply.dto.request.ReplyRequest;
import com.personal.project.controller.reply.dto.response.ReplyResponse;
import com.personal.project.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class QuestionController {

    private final QuestionService questionService;

    // 질문 작성
    @PostMapping("/{teamId}/question/create")
    public ApiSuccessResponse<QuestionResponse> createQuestion(@AuthenticationPrincipal UserDetails userDetails,
                                                               @PathVariable(name = "teamId") Long teamId,
                                                               @Valid @RequestBody QuestionRequest questionRequest) {

        return ApiSuccessResponse.from(questionService.createQuestion(userDetails, teamId, questionRequest));
    }


    //  팀 아이디를 통해 해당 질문을 조회
    @PostMapping("/{teamId}/question/get")
    public ApiSuccessResponse<List<QuestionResponse>> getStudBoardReplies(@PathVariable(name = "teamId") Long teamId,
                                                                       @RequestParam(name = "page") Integer page,
                                                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        return ApiSuccessResponse.from(questionService.getTeamQuestions(teamId, page, pageSize));
    }

    // 팀에 내가 작성한 질문 삭제
    @DeleteMapping("/{teamId}/question/delete/{questionId}")
    public ApiSuccessResponse<?> deleteReply(@AuthenticationPrincipal UserDetails userDetails,
                                             @PathVariable(name = "questionId") Long questionId) {

        questionService.deleteQuestion(userDetails, questionId);

        return ApiSuccessResponse.NO_DATA_RESPONSE;
    }

    // 팀 질문 수정
    @PutMapping("/{teamId}/question/update/{questionId}")
    public ApiSuccessResponse<QuestionResponse> updateQuestion(@AuthenticationPrincipal UserDetails userDetails,
                                                               @PathVariable(name = "questionId") Long questionId,
                                                               @RequestBody QuestionRequest questionRequest) {

        return ApiSuccessResponse.from(questionService.updateQuestion(userDetails, questionId, questionRequest));
    }

}
