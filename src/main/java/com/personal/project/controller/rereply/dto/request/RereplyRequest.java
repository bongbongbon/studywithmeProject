package com.personal.project.controller.rereply.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RereplyRequest {

    @NotBlank(message = "대댓글을 입력해주세요.")
    private String content;

}
