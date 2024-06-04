package com.personal.project.controller.team;

import com.personal.project.common.response.ApiSuccessResponse;
import com.personal.project.controller.studyboard.dto.request.StudyBoardRequest;
import com.personal.project.controller.studyboard.dto.response.StudyBoardResponse;
import com.personal.project.controller.team.dto.request.TeamRequest;
import com.personal.project.controller.team.dto.response.TeamResponse;
import com.personal.project.entity.TeamEntity;
import com.personal.project.entity.TeamUserEntity;
import com.personal.project.service.TeamService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/create")
    public ApiSuccessResponse<TeamResponse> createTeam(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody TeamRequest teamRequest) {

        String username = userDetails.getUsername();

        return ApiSuccessResponse.from(teamService.createTeam(username, teamRequest));
    }

    // teamId 받아서 하나의 팀만 가져오기
    @GetMapping("/get/{teamId}")
    public ApiSuccessResponse<TeamResponse> getOneTeam(@PathVariable(name = "teamId") Long teamId, HttpServletRequest request, HttpServletResponse response) {

        return ApiSuccessResponse.from(teamService.getOneTeam(teamId, request, response));
    }

    // 스터디게시판 삭제
    @DeleteMapping("/delete/{teamId}")
    public ApiSuccessResponse<?> deleteTeam(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "teamId") Long teamId) {

        teamService.deleteOneTeam(userDetails, teamId);

        return ApiSuccessResponse.NO_DATA_RESPONSE;
    }

    // 스터디게시판 수정
    @PostMapping("/update/{teamId}")
    public ApiSuccessResponse<TeamResponse> updateStudyBoard(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "teamId") Long teamId, @Valid @RequestBody TeamRequest teamRequest) {


        return ApiSuccessResponse.from(teamService.updateTeam(userDetails, teamId, teamRequest));
    }

    // 전체 스터디 읽어오는 api
    @GetMapping("/getAll")
    public ApiSuccessResponse<List<TeamResponse>> getAllStudyBoard(@RequestParam(name = "page") Integer page,
                                                                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        return ApiSuccessResponse.from(teamService.getAllTeam(page, pageSize));
    }

    // 팀 모집 마감로직
    @GetMapping("/changeStatus/{teamId}")
    public ApiSuccessResponse<TeamResponse> changeStatusComplete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "teamId") Long teamId) {

        return ApiSuccessResponse.from(teamService.changeStatusComplete(userDetails, teamId));
    }




}
