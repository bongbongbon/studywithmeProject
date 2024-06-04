package com.personal.project.controller.question.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {

    @NotBlank(message = "댓글을 입력해주세요.")
    private String content;
}
