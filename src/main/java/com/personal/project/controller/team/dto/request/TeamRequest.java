package com.personal.project.controller.team.dto.request;

import com.personal.project.entity.UserEntity;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequest {

    @NotBlank(message = "팀 제목을 입력하세요")
    private String title;

    @NotBlank(message = "팀 내용을 입력하세요")
    private String content;

    @NotBlank(message = "팀 지역을 입력하세요")
    private String address;

    private String detailAddress;

    @NotBlank(message = "메인 카테고리를 입력하세요")
    private String mainCategory;

    @NotBlank(message = "서브 카테고리를 입력하세요")
    private String subCategory;

    @NotNull(message = "팀 참여시작 시간을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "과거시간은 설정이 불가능합니다.")
    private LocalDate joinStartDate;

    @NotNull(message = "팀 참여마감 시간을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "미래시간만 설정 가능합니다.")
    private LocalDate joinEndDate;

    @NotNull
    @Min(1)
    @Max(5)// 최소값을 1로 설정
    private int limitTeamUserCount;

}
