package com.personal.project.controller.user.dto.response;


import com.personal.project.controller.studyboard.dto.response.StudyBoardResponse;
import com.personal.project.controller.team.dto.response.TeamResponse;
import com.personal.project.controller.teamuser.dto.response.TeamUserResponse;
import com.personal.project.entity.StudyBoardEntity;
import com.personal.project.entity.TeamEntity;
import com.personal.project.entity.TeamUserEntity;
import com.personal.project.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String username;

    private String nickName;

    private String email;

    private String phone;

    private String address;

    private String detailAddress;


    public static UserResponse fromEntity(UserEntity userEntity) {

        return UserResponse.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .nickName(userEntity.getNickName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .address(userEntity.getAddress())
                .detailAddress(userEntity.getDetailAddress())
                .build();
    }


}
