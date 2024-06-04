package com.personal.project.controller.question.dto.response;

import com.personal.project.controller.reply.dto.response.ReplyResponse;
import com.personal.project.entity.QuestionEntity;
import com.personal.project.entity.ReplyEntity;
import com.personal.project.entity.StudyBoardEntity;
import com.personal.project.entity.TeamEntity;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {

    private Long questionId;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public static QuestionResponse fromEntity(QuestionEntity question) {
        return QuestionResponse.builder()
                .questionId(question.getId())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .modifiedAt(question.getModifiedAt())
                .build();
    }
}
