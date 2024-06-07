package com.personal.project.entity;

import com.personal.project.controller.team.dto.request.TeamRequest;
import com.personal.project.controller.team.dto.response.TeamResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
@AuditOverride(forClass = BaseEntity.class)
@Table(name = "team")
public class TeamEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", unique = true, nullable = false)
    private Long id;

    private String title;

    private String content;

    private String address;

    private String detailAddress;

    private String mainCategory;

    private String subCategory;

    private int limitTeamUserCount;

    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinEndDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity user;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<TeamUserEntity> teamUserList = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<QuestionEntity> questionList = new ArrayList<>();

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    public int getCount() {
        return this.teamUserList.size();
    }

    // status가 COMPLETE가 된 유저만 카운트
    public int getUserCompleteCount() {
        return (int) teamUserList.stream()
                .filter(user -> "COMPLETE".equals(user.getStatus()))
                .count();
    }

    public static TeamResponse toResponse(TeamEntity team) {
        return TeamResponse.builder()
                .id(team.getId())
                .title(team.getTitle())
                .content(team.getContent())
                .address(team.getAddress())
                .detailAddress(team.getDetailAddress())
                .mainCategory(team.getMainCategory())
                .subCategory(team.getSubCategory())
                .limitTeamUserCount(team.getLimitTeamUserCount())
                .view(team.getView())
                .status(team.getStatus())
                .joinStartDate(team.getJoinStartDate())
                .joinEndDate(team.getJoinEndDate())
                .createdAt(team.getCreatedAt())
                .modifiedAt(team.getModifiedAt())
                .build();

    }

    public void update(TeamRequest teamRequest) {
        this.title = teamRequest.getTitle();
        this.content = teamRequest.getContent();
        this.address = teamRequest.getAddress();
        this.detailAddress = teamRequest.getDetailAddress();
        this.mainCategory = teamRequest.getMainCategory();
        this.subCategory = teamRequest.getSubCategory();
    }
}
