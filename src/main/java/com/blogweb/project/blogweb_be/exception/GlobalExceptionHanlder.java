package com.blogweb.project.blogweb_be.exception;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.blogweb.project.blogweb_be.dto.respone.ApiResponse;

import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHanlder {

    private static final String minValue = "min";

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException e) {
        ApiResponse apiRespone = ApiResponse.builder()
                .code(ErrorCode.UNKNOW_EXCEPTION.getCode())
                .message(ErrorCode.UNKNOW_EXCEPTION.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiRespone);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException e) {
        return ResponseEntity.status(e.getErrorCode().getStatusCode())
                .body(ApiResponse.builder()
                        .code(e.getErrorCode().getCode())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleException(MethodArgumentNotValidException e) {
        // Lấy message từ lỗi field validation (thường là key mapping sang enum ErrorCode)
        String enumKey = e.getFieldError().getDefaultMessage();
        log.info(enumKey);
        ErrorCode errorCode;
        Map<String, Object> attributes = null;
        try {
            // Convert enumKey sang ErrorCode enum
            errorCode = ErrorCode.valueOf(enumKey);
            // Lấy lỗi đầu tiên trong BindingResult, unwrap thành ConstraintViolation để lấy metadata
            var constraintViolation = e.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);
            log.info((constraintViolation).toString());
            // Lấy attributes từ annotation validation (ví dụ @Min(18) => {value=18})
            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
        } catch (IllegalArgumentException ex) {
            errorCode = ErrorCode.ENUMKEY_INVALID;
        }
        ApiResponse apiRespone = ApiResponse.builder()
                .code(errorCode.getCode())
                // Nếu có attributes => thay placeholder trong message bằng giá trị thật
                .message(Objects.nonNull(attributes)
                        ? mapAttribute(errorCode.getMessage(), attributes) : errorCode.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiRespone);
    }
// Hàm thay thế placeholder trong message bằng giá trị attributes (ví dụ: {minDValue} -> 18)

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String value = String.valueOf(attributes.get(minValue));
        return message.replace("{" + minValue + "}", value);
    }
}
