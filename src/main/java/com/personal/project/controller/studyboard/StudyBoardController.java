package com.personal.project.controller.studyboard;

import com.personal.project.common.response.ApiSuccessResponse;
import com.personal.project.controller.studyboard.dto.request.StudyBoardRequest;
import com.personal.project.controller.studyboard.dto.response.StudyBoardResponse;
import com.personal.project.entity.StudyBoardEntity;
import com.personal.project.service.StudyBoardService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studyBoard")
public class StudyBoardController {

    private final StudyBoardService studyBoardService;


    // 스터디게시판 만드는 api
    @PostMapping("/create")
    public ApiSuccessResponse<?> createStudyBoard(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody StudyBoardRequest studyBoardRequest) {

        String username = userDetails.getUsername();

        return ApiSuccessResponse.from(studyBoardService.createStudyBoard(username, studyBoardRequest));
    }

    // studyBoardId를 받아서 하나의 스터디게시판만 가져오기
    @GetMapping("/get/{studyBoardId}")
    public ApiSuccessResponse<?> getOneStudyBoard(@PathVariable(name = "studyBoardId") Long studyBoardId, HttpServletRequest request,HttpServletResponse response) {

        return ApiSuccessResponse.from(studyBoardService.getOneStudyBoard(studyBoardId, request, response));
    }

    // 스터디게시판 삭제
    @DeleteMapping("/delete/{studyBoardId}")
    public ApiSuccessResponse<?> deleteStudyBoard(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "studyBoardId") Long studyBoardId) {

        studyBoardService.deleteOneStudyBoard(userDetails, studyBoardId);

        return ApiSuccessResponse.NO_DATA_RESPONSE;
    }

    // 스터디게시판 수정
    @PostMapping("/update/{studyBoardId}")
    public ApiSuccessResponse<?> updateStudyBoard(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "studyBoardId") Long studyBoardId, @Valid @RequestBody StudyBoardRequest studyBoardRequest) {


        return ApiSuccessResponse.from(studyBoardService.updateStudyBoard(userDetails, studyBoardId, studyBoardRequest));
    }


    // 전체 스터디 읽어오는 api
    @GetMapping("/getAll")
    public ApiSuccessResponse<List<StudyBoardResponse>> getAllStudyBoard(@RequestParam(name = "page") Integer page,
                                                                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        return ApiSuccessResponse.from(studyBoardService.getAllStudyBoard(page, pageSize));
    }








}
