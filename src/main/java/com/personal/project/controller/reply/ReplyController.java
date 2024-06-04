package com.personal.project.controller.reply;

import com.personal.project.common.response.ApiSuccessResponse;
import com.personal.project.controller.reply.dto.request.ReplyRequest;
import com.personal.project.controller.reply.dto.response.ReplyResponse;
import com.personal.project.entity.ReplyEntity;
import com.personal.project.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studyBoard")
public class ReplyController {

    private final ReplyService replyService;

    // 댓글 작성
    @PostMapping("/{studyBoardId}/reply/create")
    public ApiSuccessResponse<ReplyResponse> createReply(@AuthenticationPrincipal UserDetails userDetails,
                                                         @PathVariable(name = "studyBoardId") Long studyBoardId,
                                                         @Valid @RequestBody ReplyRequest replyRequest) {

        return ApiSuccessResponse.from(replyService.createReply(userDetails, studyBoardId, replyRequest));
    }


    // 스터디게시판 아이디를 통해서 해당 댓글을 조회
    @PostMapping("/{studyBoardId}/reply/get")
    public ApiSuccessResponse<List<ReplyResponse>> getStudBoardReplies(@PathVariable(name = "studyBoardId") Long studyBoardId,
                                                                     @RequestParam(name = "page") Integer page,
                                                                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        return ApiSuccessResponse.from(replyService.getStudBoardReplies(studyBoardId, page, pageSize));
    }

    // 스터디게시판에 내가 작성한 댓글 삭제
    @DeleteMapping("/{studyBoardId}/reply/delete/{replyId}")
    public ApiSuccessResponse<?> deleteReply(@AuthenticationPrincipal UserDetails userDetails,
                                             @PathVariable(name = "replyId") Long replyId) {

        replyService.deleteReply(userDetails, replyId);

        return ApiSuccessResponse.NO_DATA_RESPONSE;
    }

    // 스터디게시판에 내가 작성한 댓글 삭제
    @PutMapping("/{studyBoardId}/reply/update/{replyId}")
    public ApiSuccessResponse<ReplyResponse> updateReply(@AuthenticationPrincipal UserDetails userDetails,
                                             @PathVariable(name = "replyId") Long replyId, @RequestBody ReplyRequest replyRequest) {

        return ApiSuccessResponse.from(replyService.updateReply(userDetails, replyId, replyRequest));
    }


}
