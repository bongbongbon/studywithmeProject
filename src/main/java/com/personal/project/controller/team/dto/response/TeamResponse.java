package com.personal.project.controller.team.dto.response;

import com.personal.project.controller.studyboard.dto.response.StudyBoardResponse;
import com.personal.project.controller.teamuser.dto.response.TeamUserResponse;
import com.personal.project.controller.user.dto.response.UserResponse;
import com.personal.project.entity.StudyBoardEntity;
import com.personal.project.entity.TeamEntity;
import com.personal.project.entity.TeamUserEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {

    private Long id;

    private String title;

    private String content;

    private String address;

    private String detailAddress;

    private String mainCategory;

    private String subCategory;

    private int view;

    private int limitTeamUserCount;

    private String status;

    private LocalDate joinStartDate;

    private LocalDate joinEndDate;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private List<TeamUserResponse> teamUserList = new ArrayList<>();

    public static TeamResponse fromEntity(TeamEntity teamEntity) {


//        teamEntity.getTeamUserList() != null ? teamEntity.getTeamUserList()
//                .stream()
//                .map(TeamUserEntity::toResponse)
//                .collect(Collectors.toList())
//                : new ArrayList<>()

//        Optional.ofNullable(teamEntity.getTeamUserList()).orElseGet(Collections::emptyList).stream().map(TeamUserEntity::toResponse).collect(Collectors.toList());


        return TeamResponse.builder()
                .id(teamEntity.getId())
                .title(teamEntity.getTitle())
                .content(teamEntity.getContent())
                .address(teamEntity.getAddress())
                .detailAddress(teamEntity.getDetailAddress())
                .mainCategory(teamEntity.getMainCategory())
                .subCategory(teamEntity.getSubCategory())
                .joinStartDate(teamEntity.getJoinStartDate())
                .joinEndDate(teamEntity.getJoinEndDate())
                .view(teamEntity.getView())
                .limitTeamUserCount(teamEntity.getLimitTeamUserCount())
                .status(teamEntity.getStatus())
                .teamUserList(Optional.ofNullable(teamEntity.getTeamUserList())
                        .orElseGet(Collections::emptyList)
                        .stream()
                        .map(TeamUserEntity::toResponse)
                        .collect(Collectors.toList()))
                .createdAt(teamEntity.getCreatedAt())
                .modifiedAt(teamEntity.getModifiedAt())
                .build();
    }


}
