package com.personal.project.common.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.project.common.exception.CustomException;
import com.personal.project.common.response.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ErrorController {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiErrorResponse> handleCustomException(CustomException e) {

        return ResponseEntity.status(e.getHttpStatus()).body(ApiErrorResponse.from(e.getErrorCode()));
    }

    // 유효성 검사 오류
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.badRequest().body(errors);
    }

    // 예상치 못한 에러 처리
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    protected ApiErrorResponse handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage());
        return ApiErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR);
    }

}
