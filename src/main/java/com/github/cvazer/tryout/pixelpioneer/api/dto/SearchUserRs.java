package com.github.cvazer.tryout.pixelpioneer.api.dto;

import com.github.cvazer.tryout.pixelpioneer.api.ApiResponse;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collection;

@Getter
public class SearchUserRs extends ApiResponse<Collection<UserDto>> implements Serializable {
    private final int page;
    private final int pageSize;
    private final int totalPages;

    public SearchUserRs(Collection<UserDto> data, int page, int pageSize, int totalPages) {
        super(data);
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
    }
}
