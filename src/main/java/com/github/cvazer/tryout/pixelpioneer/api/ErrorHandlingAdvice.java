package com.github.cvazer.tryout.pixelpioneer.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandlingAdvice {
    public static final int VALIDATION_ERROR_CODE = 400;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> fallbackHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ApiResponse<>(e);
    }

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> apiExceptionHandler(ApiException e) {
        if (e.print) {
            log.info(e.getMessage(), e);
        } else {
            log.debug(e.getMessage(), e);
        }
        return new ApiResponse<>(e);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> validationBindException(BindException e) {
        log.trace(e.getMessage(), e);
        return new ApiResponse<>(new ErrorInfo(VALIDATION_ERROR_CODE, "Malformed request parameter"));
    }

}
