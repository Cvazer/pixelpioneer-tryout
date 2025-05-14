package com.github.cvazer.tryout.pixelpioneer.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T>{
    private final ErrorInfo errorInfo;
    private final @JsonInclude(JsonInclude.Include.NON_NULL) T data;

    public ApiResponse() {
        this(new ErrorInfo(), null);
    }

    public ApiResponse(T data) {
        this(new ErrorInfo(), data);
    }

    public ApiResponse(ErrorInfo errorInfo) {
        this(errorInfo, null);
    }

    public ApiResponse(Throwable throwable) {
        this(new ErrorInfo(throwable), null);
    }
}
