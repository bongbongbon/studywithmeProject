package com.personal.project.controller.answer.dto.response;


import com.personal.project.controller.rereply.dto.response.RereplyResponse;
import com.personal.project.entity.AnswerEntity;
import com.personal.project.entity.RereplyEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponse {

    private Long answerId;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public static AnswerResponse fromEntity(AnswerEntity answer) {
        return AnswerResponse.builder()
                .answerId(answer.getId())
                .content(answer.getContent())
                .createdAt(answer.getCreatedAt())
                .modifiedAt(answer.getModifiedAt())
                .build();
    }
}
