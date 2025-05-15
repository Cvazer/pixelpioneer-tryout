package com.github.cvazer.tryout.pixelpioneer.api.controller;

import com.github.cvazer.tryout.pixelpioneer.api.ApiResponse;
import com.github.cvazer.tryout.pixelpioneer.api.dto.SearchUserRq;
import com.github.cvazer.tryout.pixelpioneer.api.dto.SearchUserRs;
import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import com.github.cvazer.tryout.pixelpioneer.service.facade.userdata.UserDataFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    public static final String SEARCH_CACHE_KEY = "search";
    private final UserDataFacade userDataFacade;

    @GetMapping
    public ApiResponse<UserDto> get() {
        return new ApiResponse<>(userDataFacade.getCurrentUserDto());
    }

    @PutMapping
    @CacheEvict(value = SEARCH_CACHE_KEY, allEntries = true)
    public ApiResponse<UserDto> update(@Valid @RequestBody UserDto dto)  {
        return new ApiResponse<>(userDataFacade.update(dto));
    }

    @Cacheable(value = SEARCH_CACHE_KEY)
    @GetMapping("/search")
    public SearchUserRs search(@Valid @RequestBody SearchUserRq rq) {
        return userDataFacade.search(rq);
    }

}
