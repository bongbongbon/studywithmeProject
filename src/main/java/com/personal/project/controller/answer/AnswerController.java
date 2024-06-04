package com.personal.project.controller.answer;

import com.personal.project.common.response.ApiSuccessResponse;
import com.personal.project.controller.answer.dto.request.AnswerRequest;
import com.personal.project.controller.answer.dto.response.AnswerResponse;
import com.personal.project.controller.rereply.dto.request.RereplyRequest;
import com.personal.project.controller.rereply.dto.response.RereplyResponse;
import com.personal.project.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/{teamId}/questions/{questionId}/answers")
    public ApiSuccessResponse<AnswerResponse> createAnswer(@AuthenticationPrincipal UserDetails userDetails,
                                                           @PathVariable(name = "teamId") Long teamId,
                                                           @PathVariable(name = "questionId") Long questionId,
                                                           @RequestBody AnswerRequest answerRequest) {


        return ApiSuccessResponse.from(answerService.createAnswer(userDetails, questionId, answerRequest));
    }

    @GetMapping("/{teamId}/questions/{questionId}/answers")
    public ApiSuccessResponse<List<AnswerResponse>> getAnswerList(
            @PathVariable(name = "teamId") Long teamId, @PathVariable(name = "questionId") Long questionId
    ){
        return ApiSuccessResponse.from(
                answerService.getAnswerList(questionId)
        );
    }

    @PutMapping("/{teamId}/questions/{questionId}/answers/{answerId}")
    public ApiSuccessResponse<AnswerResponse> updateAnswer(@AuthenticationPrincipal UserDetails userDetails,
                                                             @PathVariable(name = "teamId") Long teamId,
                                                             @PathVariable(name = "questionId") Long questionId,
                                                             @PathVariable(name = "answerId") Long answerId,
                                                             @RequestBody AnswerRequest answerRequest){

        return ApiSuccessResponse.from(answerService.updateAnswer(userDetails,questionId, answerId, answerRequest));
    }

    @DeleteMapping("/{teamId}/questions/{questionId}/answers/{answerId}")
    public void deleteAnswer(  @AuthenticationPrincipal UserDetails userDetails,
                                @PathVariable(name = "teamId") Long teamId, @PathVariable(name = "questionId") Long questionId,
                                @PathVariable(name = "answerId") Long answerId) {

        answerService.deleteAnswer(userDetails, questionId, answerId);
    }
}
