package com.personal.project.controller.reply.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRequest {

    @NotBlank(message = "댓글을 입력해주세요.")
    private String content;

}
