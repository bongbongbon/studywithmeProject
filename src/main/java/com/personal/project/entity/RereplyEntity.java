package com.personal.project.entity;

import com.personal.project.controller.rereply.dto.response.RereplyResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AuditOverride(forClass = BaseEntity.class)
@Table(name = "rereply")
@Entity
public class RereplyEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rereply_id")
    private Long id;

    private String content;

    // 1:N mapping with User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity user;

    // 1:N mapping with Reply
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    @ToString.Exclude
    private ReplyEntity reply;


    public static RereplyResponse toResponse(RereplyEntity rereply) {
        return RereplyResponse.builder()
                .rereplyId(rereply.getId())
                .content(rereply.getContent())
                .createdAt(rereply.getCreatedAt())
                .modifiedAt(rereply.getModifiedAt())
                .build();
    }

}
