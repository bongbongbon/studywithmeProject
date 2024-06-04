package com.personal.project.controller.reply.dto.response;

import com.personal.project.controller.rereply.dto.response.RereplyResponse;
import com.personal.project.entity.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponse {

    private Long replyId;

    private String content;

    private String nickName;

    private List<RereplyResponse> rereplyList = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public static ReplyResponse fromEntity(ReplyEntity reply) {
        return ReplyResponse.builder()
                .replyId(reply.getId())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .modifiedAt(reply.getModifiedAt())
                .build();
    }


}
