package com.test.config;

import com.test.config.exception.ConflictException;
import com.test.config.exception.DataNotFoundException;
import com.test.global.model.enumtype.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 데이터를 찾지 못했을 때 발생한다.
     */
    @ExceptionHandler(DataNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException e, HttpServletRequest request) {
        log.error("handleDataNotFoundException", e);
        final ErrorCode errorCode = ErrorCode.DATA_NOT_FOUND;
        final String errorMessage = StringUtils.hasText(e.getMessage()) ? e.getMessage() : errorCode.getMessage();
        final ErrorResponse errorResponse = ErrorResponse.builder(e, errorCode.getStatus(), errorMessage)
                .title(errorCode.getMessage())
                .instance(URI.create(request.getRequestURI()))
                .property("timestamp", LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * 이미 데이터가 존재하는경우 발생한다
     */
    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<ErrorResponse> handleConflictException(ConflictException e, HttpServletRequest request) {
        final ErrorCode errorCode = ErrorCode.CONFLICT;
        final String errorMessage = e.getMessage();
        final ErrorResponse errorResponse = ErrorResponse.builder(e, errorCode.getStatus(), errorMessage)
                .title(errorCode.getMessage())
                .instance(URI.create(request.getRequestURI()))
                .property("timestamp", LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * 잘못된 요청 파라미터에 대한 처리
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected  ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        final ErrorCode errorCode = ErrorCode.BAD_REQUEST;
        final String errorMessage = e.getMessage();
        final ErrorResponse errorResponse = ErrorResponse.builder(e, errorCode.getStatus(), errorMessage)
                .title(errorCode.getMessage())
                .instance(URI.create(request.getRequestURI()))
                .property("timestamp", LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * 상기 이외의 익셉션 공통 핸들링
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity handleException(Exception e) {
        log.error("handleException", e);
        final ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        final String errorMessage = e.getMessage();
        final ErrorResponse errorResponse = ErrorResponse.builder(e, errorCode.getStatus(), errorMessage)
                .title(errorCode.getMessage())
                .property("timestamp", LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }
}
