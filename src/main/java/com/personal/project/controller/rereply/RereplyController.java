package com.personal.project.controller.rereply;

import com.personal.project.common.response.ApiSuccessResponse;
import com.personal.project.controller.rereply.dto.request.RereplyRequest;
import com.personal.project.controller.rereply.dto.response.RereplyResponse;
import com.personal.project.entity.RereplyEntity;
import com.personal.project.service.RereplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studyBoard")
public class RereplyController {

    private final RereplyService rereplyService;

    @PostMapping("/{studyBoardId}/replies/{replyId}/rereplies")
    public ApiSuccessResponse<RereplyResponse> createRereply(@AuthenticationPrincipal UserDetails userDetails,
                                                             @PathVariable(name = "studyBoardId") Long studyBoardId,
                                                             @PathVariable(name = "replyId") Long replyId,
                                                             @RequestBody RereplyRequest rereplyRequest) {


        return ApiSuccessResponse.from(rereplyService.createRereply(userDetails, replyId, rereplyRequest));
    }

    @GetMapping("/{studyBoardId}/replies/{replyId}/rereplies")
    public ApiSuccessResponse<List<RereplyResponse>> getRereplyList(
            @PathVariable(name = "studyBoardId") Long studyBoardId, @PathVariable(name = "replyId") Long replyId
    ){
        return ApiSuccessResponse.from(
                rereplyService.getRereplyList(replyId)
        );
    }

    @PutMapping("/{studyBoardId}/replies/{replyId}/rereplies/{rereplyId}")
    public ApiSuccessResponse<RereplyResponse> updateRereply(@AuthenticationPrincipal UserDetails userDetails,
                                                             @PathVariable(name = "studyBoardId") Long studyBoardId,
                                                             @PathVariable(name = "replyId") Long replyId,
                                                             @PathVariable(name = "rereplyId") Long rereplyId,
                                                             @RequestBody RereplyRequest rereplyRequest){

        return ApiSuccessResponse.from(rereplyService.updateRereply(userDetails,replyId, rereplyId, rereplyRequest));
    }

    @DeleteMapping("/{studyBoardId}/replies/{replyId}/rereplies/{rereplyId}")
    public void deleteRereply(  @AuthenticationPrincipal UserDetails userDetails,
                                @PathVariable(name = "studyBoardId") Long studyBoardId, @PathVariable(name = "replyId") Long replyId,
                                @PathVariable(name = "rereplyId") Long rereplyId) {

        rereplyService.deleteRereply(userDetails, replyId, rereplyId);
    }


}
