package com.personal.project.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.project.common.exception.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiErrorResponse {

    private HttpStatus httpStatus;

    private String errorCode;

    private String errorMessage;

    public static ApiErrorResponse from(ErrorCode errorCode) {
        return ApiErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .errorMessage(errorCode.getMessage())
                .httpStatus(errorCode.getHttpStatus())
                .build();
    }

    public void changeMessage(String message) {
        this.errorMessage = message;


    }
}
