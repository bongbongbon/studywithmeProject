package com.personal.project.controller.studyboard.dto.response;

import com.personal.project.controller.reply.dto.response.ReplyResponse;
import com.personal.project.entity.ReplyEntity;
import com.personal.project.entity.StudyBoardEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyBoardResponse {

    private Long id;

    private String title;

    private String content;

    private String address;

    private String detailAddress;

    private String mainCategory;

    private String subCategory;

    private int view;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public static StudyBoardResponse fromEntity(StudyBoardEntity studyBoardEntity) {
        return StudyBoardResponse.builder()
                .id(studyBoardEntity.getId())
                .title(studyBoardEntity.getTitle())
                .content(studyBoardEntity.getContent())
                .address(studyBoardEntity.getAddress())
                .detailAddress(studyBoardEntity.getDetailAddress())
                .mainCategory(studyBoardEntity.getMainCategory())
                .subCategory(studyBoardEntity.getSubCategory())
                .view(studyBoardEntity.getView())
                .createdAt(studyBoardEntity.getCreatedAt())
                .modifiedAt(studyBoardEntity.getModifiedAt())
                .build();
    }

}
