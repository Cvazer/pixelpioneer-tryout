package com.github.cvazer.tryout.pixelpioneer.service.model;

import com.github.cvazer.tryout.pixelpioneer.api.ApiResponse;
import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class SearchUserResult extends ApiResponse<List<UserDto>> implements Serializable {
    private final int pageIndex;
    private final int pageSize;
    private final int totalPages;

    public SearchUserResult(List<UserDto> data, int pageIndex, int pageSize, int totalPages) {
        super(data);
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
    }
}
