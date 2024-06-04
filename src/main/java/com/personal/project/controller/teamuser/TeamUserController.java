package com.personal.project.controller.teamuser;

import com.personal.project.common.response.ApiSuccessResponse;
import com.personal.project.controller.team.dto.response.TeamResponse;
import com.personal.project.controller.teamuser.dto.response.TeamUserResponse;
import com.personal.project.repository.TeamUserRepository;
import com.personal.project.service.TeamUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamUserController {

    private final TeamUserService teamUserService;

    @PostMapping("/{teamId}/join/teamUser")
    public ApiSuccessResponse<TeamUserResponse> joinTeamUser(@AuthenticationPrincipal UserDetails userDetails,
                                                             @PathVariable(name = "teamId") Long teamId) {


       return ApiSuccessResponse.from(teamUserService.joinTeamUser(userDetails, teamId));
    }

    // 팀유저 status 진행중에서 status 완료로 변경
    @PostMapping("/{teamId}/teamUser/changeStatus/{teamUserId}")
    public ApiSuccessResponse<TeamUserResponse> changeTeamUserStatusComplete(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @PathVariable(name = "teamId") Long teamId,
                                                                 @PathVariable(name = "teamUserId") Long teamUserId) {


        return ApiSuccessResponse.from(teamUserService.changeTeamUserStatusComplete(userDetails, teamId, teamUserId));
    }

    // 팀리더가 참여취소를 누를시 삭제와 마감기한이 지났는데 status가 complete 되지 않았을 때 삭제
    @DeleteMapping("/{teamId}/teamUser/delete/{teamUserId}")
    public ApiSuccessResponse<?> deleteTeamUser(@AuthenticationPrincipal UserDetails userDetails,
                                                                     @PathVariable(name = "teamId") Long teamId,
                                                                     @PathVariable(name = "teamUserId") Long teamUserId) {

        teamUserService.deleteTeamUser(userDetails, teamId, teamUserId);

        return ApiSuccessResponse.NO_DATA_RESPONSE;
    }




}
