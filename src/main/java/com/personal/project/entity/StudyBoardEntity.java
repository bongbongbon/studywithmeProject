package com.personal.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.personal.project.controller.studyboard.dto.response.StudyBoardResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@AuditOverride(forClass = BaseEntity.class)
@ToString
@Table(name = "studyboard")
public class StudyBoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studyboard_id", unique = true, nullable = false)
    private Long id;

    private String title;

    private String content;

    private String address;

    private String detailAddress;

    private String mainCategory;

    private String subCategory;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity user;

    @OneToMany(mappedBy = "studyBoard")
    @ToString.Exclude
    @Builder.Default
    private List<ReplyEntity> replyList = new ArrayList<>();


    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;




    public static StudyBoardResponse toResponse(StudyBoardEntity studyBoardEntity) {
        return StudyBoardResponse.builder()
                .id(studyBoardEntity.getId())
                .title(studyBoardEntity.getTitle())
                .content(studyBoardEntity.getContent())
                .address(studyBoardEntity.getAddress())
                .detailAddress(studyBoardEntity.getDetailAddress())
                .mainCategory(studyBoardEntity.getMainCategory())
                .subCategory(studyBoardEntity.getSubCategory())
                .createdAt(studyBoardEntity.getCreatedAt())
                .modifiedAt(studyBoardEntity.getModifiedAt())
                .build();
    }
}
