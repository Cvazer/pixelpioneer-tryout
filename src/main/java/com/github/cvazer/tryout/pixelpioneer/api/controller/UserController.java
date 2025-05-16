package com.github.cvazer.tryout.pixelpioneer.api.controller;

import com.github.cvazer.tryout.pixelpioneer.api.ApiResponse;
import com.github.cvazer.tryout.pixelpioneer.api.dto.SearchUserParams;
import com.github.cvazer.tryout.pixelpioneer.api.dto.SearchUserResult;
import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import com.github.cvazer.tryout.pixelpioneer.service.facade.userdata.UserDataFacade;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    public static final String SEARCH_CACHE_KEY = "search";
    private final UserDataFacade userDataFacade;

    @GetMapping
    @Operation(summary = "Current user information view")
    public ApiResponse<UserDto> get() {
        return new ApiResponse<>(userDataFacade.getCurrentUserDto());
    }

    @PutMapping
    @CacheEvict(value = SEARCH_CACHE_KEY, allEntries = true)
    @Operation(summary = "Updates user's email and phone data. Note: other fields are ignored")
    public ApiResponse<UserDto> update(@Valid @RequestBody UserDto dto)  {
        return new ApiResponse<>(userDataFacade.update(dto));
    }

    @ApiImplicitParam(name = HttpHeaders.CONTENT_TYPE, value = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Search for users based on request body")
    @Cacheable(value = SEARCH_CACHE_KEY)
    @GetMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SearchUserResult searchWithRq(@Nullable @Valid @RequestBody SearchUserParams rq) {
        return userDataFacade.search(rq);
    }

    /*
    Из-за двойного маппинга свагер не видит второго эндпоинта. Решение есть, но я вижу его
    за рамками тестового залания.
    */
    @Operation(summary = "Search for users based on number of params")
    @Cacheable(value = SEARCH_CACHE_KEY)
    @GetMapping(value = "/search", consumes = MediaType.ALL_VALUE)
    public SearchUserResult searchWithParams(
            @Nullable @RequestParam Integer page,
            @Nullable @RequestParam Integer pageSize,
            @Nullable @RequestParam String name,
            @Nullable @RequestParam String dateOfBirth,
            @Nullable @RequestParam String email,
            @Nullable @RequestParam String phone
    ) {
        return userDataFacade.search(page, pageSize, name, dateOfBirth, email, phone);
    }


}
