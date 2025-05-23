package com.github.cvazer.tryout.pixelpioneer.api;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiException extends RuntimeException {
    protected boolean print = true;
    protected Integer code;

    public ApiException(boolean print, Integer code, Exception e) {
        this(e);
        this.print = print;
        this.code = code;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}
