package com.personal.project.controller.answer.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequest {

    @NotBlank(message = "답변을 입력해주세요.")
    private String content;

}
