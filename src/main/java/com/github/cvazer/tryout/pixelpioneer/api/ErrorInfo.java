package com.github.cvazer.tryout.pixelpioneer.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class ErrorInfo implements Serializable {
    public static final int OK_CODE = 0;
    public static final int DEFAULT_ERROR_CODE = 1;

    private final int code;
    private final String description;

    public ErrorInfo() {
        this(OK_CODE, null);
    }

    public ErrorInfo(Throwable ex) {
        this(DEFAULT_ERROR_CODE, ex.getMessage());
    }

    public ErrorInfo(ApiException ex) {
        this(Optional.ofNullable(ex.getCode()).orElse(DEFAULT_ERROR_CODE), ex.getMessage());
    }

}
