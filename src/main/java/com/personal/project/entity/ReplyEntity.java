package com.personal.project.entity;


import com.personal.project.controller.reply.dto.response.ReplyResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AuditOverride(forClass = BaseEntity.class)
@Table(name = "reply")
@Entity
public class ReplyEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String content;

    // 1:N mapping with User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyboard_id")
    @ToString.Exclude
    private StudyBoardEntity studyBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @ToString.Exclude
    private TeamEntity team;


    @OneToMany(mappedBy = "reply")
    @ToString.Exclude
    private List<RereplyEntity> rereplyList = new ArrayList<>();

    public static ReplyResponse toResponse(ReplyEntity reply) {
        return ReplyResponse.builder()
                .replyId(reply.getId())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .modifiedAt(reply.getModifiedAt())
                .build();
    }

}
