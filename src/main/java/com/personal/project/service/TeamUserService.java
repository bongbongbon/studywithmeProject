package com.personal.project.service;


import com.personal.project.common.exception.CustomException;
import com.personal.project.common.exception.ErrorCode;
import com.personal.project.controller.teamuser.dto.response.TeamUserResponse;
import com.personal.project.entity.TeamEntity;
import com.personal.project.entity.TeamUserEntity;
import com.personal.project.entity.UserEntity;
import com.personal.project.repository.TeamRepository;
import com.personal.project.repository.TeamUserRepository;
import com.personal.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamUserService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;

    @Transactional
    public TeamUserResponse joinTeamUser(UserDetails userDetails, Long teamId) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));


        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        // 팀모집을 마감시켰을 때
        if (team.getStatus().equals("COMPLETE")) {
            throw new CustomException(ErrorCode.ALREADY_TEAM_IS_COMPLETE);
        }

        // 이미 이팀에 참여했을 경우
        if (team.getTeamUserList().stream().anyMatch(teamUser -> user.getUsername().equals(teamUser.getUser().getUsername()))) {
            throw new CustomException(ErrorCode.ALREADY_JOIN_THIS_TEAM);
        }

        // 팀원중 complete 된 모집인원이 다 찼을때 (팀리더 포함 갯수)
        if (team.getUserCompleteCount() >= team.getLimitTeamUserCount()) {
            throw new CustomException(ErrorCode.TEAM_USER_IS_FULL);
        }

        // 팀 마감기한이 끝났을 때
        if (team.getJoinEndDate().equals(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.JOIN_END_DATE_IS_OVER);
        }



        TeamUserEntity teamUser = TeamUserEntity.builder()
                .team(team)
                .user(user)
                .status("PROCEEDING")
                .role("TEAM_USER")
                .build();

        //complete

        return TeamUserResponse.fromEntity(teamUserRepository.save(teamUser));
    }

    @Transactional
    public TeamUserResponse changeTeamUserStatusComplete(UserDetails userDetails, Long teamId, Long teamUserId) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));


        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));


        TeamUserEntity teamUser = teamUserRepository.findById(teamUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_USER_NOT_FOUND));

        // 팀을 만든 사람이 아니면 status를 변경하지 못하게 하는 로직
        if (!team.getUser().getUsername().equals(userDetails.getUsername()) ) {
            throw new CustomException(ErrorCode.USER_INFO_NOT_MATCH);
        }

         teamUser.setStatus("COMPLETE");

        return TeamUserResponse.fromEntity(teamUserRepository.save(teamUser));

    }

    // Cancel 을 누를 경우 teamUser 삭제
    @Transactional
    public void deleteTeamUser(UserDetails userDetails, Long teamId, Long teamUserId) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));


        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));


        TeamUserEntity teamUser = teamUserRepository.findById(teamUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_USER_NOT_FOUND));

        // 팀을 만든 사람이 아니면 status를 변경하지 못하게 하는 로직
        if (!team.getUser().getUsername().equals(userDetails.getUsername()) ) {
            throw new CustomException(ErrorCode.USER_INFO_NOT_MATCH);
        }

        // 리더는 삭제 불가.
        if(teamUser.getRole().equals("TEAM_READER")) {
            throw new CustomException(ErrorCode.CAN_NOT_DELETE_TEAM_LEADER);
        }


        // 팀신청 마감기한이 끝나고 status가 완료가 되지 않았을 때 팀유저 삭제
        if (team.getJoinEndDate().equals(LocalDateTime.now()) && !teamUser.getStatus().equals("COMPLETE")) {
            teamUserRepository.deleteById(teamUserId);
        }

        teamUserRepository.deleteById(teamUserId);

    }


}

