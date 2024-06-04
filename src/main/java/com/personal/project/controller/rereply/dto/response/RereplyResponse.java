package com.personal.project.controller.rereply.dto.response;

import com.personal.project.controller.reply.dto.response.ReplyResponse;
import com.personal.project.entity.ReplyEntity;
import com.personal.project.entity.RereplyEntity;
import com.personal.project.entity.StudyBoardEntity;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RereplyResponse {

    private Long rereplyId;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public static RereplyResponse fromEntity(RereplyEntity rereply) {
        return RereplyResponse.builder()
                .rereplyId(rereply.getId())
                .content(rereply.getContent())
                .createdAt(rereply.getCreatedAt())
                .modifiedAt(rereply.getModifiedAt())
                .build();
    }
}
