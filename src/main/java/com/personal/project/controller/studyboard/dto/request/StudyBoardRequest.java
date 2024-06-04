package com.personal.project.controller.studyboard.dto.request;

import com.personal.project.entity.StudyBoardEntity;
import com.personal.project.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyBoardRequest {

    @NotBlank(message = "제목을 입력하세요")
    private String title;

    @NotBlank(message = "내용을 입력하세요")
    private String content;

    @NotBlank(message = "주소를 입력하세요")
    private String address;

    private String detailAddress;

    @NotBlank(message = "메인 카테고리를 입력하세요")
    private String mainCategory;

    @NotBlank(message = "서브 카테고리를 입력하세요")
    private String subCategory;



}
