package com.personal.project.entity;

import com.personal.project.controller.team.dto.response.TeamResponse;
import com.personal.project.controller.teamuser.dto.response.TeamUserResponse;
import com.personal.project.controller.user.dto.response.UserResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
@AuditOverride(forClass = BaseEntity.class)
@Table(name = "teamUser")
public class TeamUserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamuser_id", unique = true, nullable = false)
    private Long id;

    private String status;

    private String role;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @ToString.Exclude
    private TeamEntity team;






    public static TeamUserResponse toResponse(TeamUserEntity teamUser) {

        // 순환 참조가 일어나 Response로 받아서 전달
        UserResponse user = UserResponse.fromEntity(teamUser.getUser());

        return TeamUserResponse.builder()
                .id(teamUser.getId())
                .user(user)
                .status(teamUser.getStatus())
                .role(teamUser.getRole())
                .createdAt(teamUser.getCreatedAt())
                .modifiedAt(teamUser.getModifiedAt())
                .build();
    }

}
