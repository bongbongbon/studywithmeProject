package com.personal.project.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class CustomException extends RuntimeException{

    private ErrorCode errorCode;
    private HttpStatus httpStatus;
    private String code;
    private String errorMessage;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
        this.code = errorCode.getCode();
        this.errorMessage = errorCode.getMessage();

    }

}
