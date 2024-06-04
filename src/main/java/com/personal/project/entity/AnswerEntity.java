package com.personal.project.entity;


import com.personal.project.controller.answer.dto.response.AnswerResponse;
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
@Table(name = "answer")
@Entity
public class AnswerEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    private String content;

    // 1:N mapping with User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity user;

    // 1:N mapping with Reply
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question")
    @ToString.Exclude
    private QuestionEntity question;


    public static AnswerResponse toResponse(AnswerEntity answer) {
        return AnswerResponse.builder()
                .answerId(answer.getId())
                .content(answer.getContent())
                .createdAt(answer.getCreatedAt())
                .modifiedAt(answer.getModifiedAt())
                .build();
    }
}
