package com.personal.project.controller.teamuser.dto.response;


import com.personal.project.controller.team.dto.response.TeamResponse;
import com.personal.project.controller.user.dto.response.UserResponse;
import com.personal.project.entity.TeamEntity;
import com.personal.project.entity.TeamUserEntity;
import com.personal.project.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamUserResponse {

    private Long id;

    private UserResponse user;

    private String status;

    private String role;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;


    public static TeamUserResponse fromEntity(TeamUserEntity teamUser) {

                // 순환 참조가 일어나 Response로 받아서 전달
                UserResponse user = UserResponse.fromEntity(teamUser.getUser());

        return TeamUserResponse.builder()
                .id(teamUser.getId())
                .user(user)
                .role(teamUser.getRole())
                .status(teamUser.getStatus())
                .createdAt(teamUser.getCreatedAt())
                .modifiedAt(teamUser.getModifiedAt())
                .build();
    }


}
