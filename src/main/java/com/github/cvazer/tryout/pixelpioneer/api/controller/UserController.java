package com.github.cvazer.tryout.pixelpioneer.api.controller;

import com.github.cvazer.tryout.pixelpioneer.api.ApiResponse;
import com.github.cvazer.tryout.pixelpioneer.service.model.SearchUserParams;
import com.github.cvazer.tryout.pixelpioneer.service.model.SearchUserResult;
import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import com.github.cvazer.tryout.pixelpioneer.service.facade.userdata.UserDataFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    public ApiResponse<UserDto> get() {
        return new ApiResponse<>(userDataFacade.getCurrentUserDto());
    }

    @PutMapping
    @CacheEvict(value = SEARCH_CACHE_KEY, allEntries = true)
    public ApiResponse<UserDto> update(@Valid @RequestBody UserDto dto)  {
        return new ApiResponse<>(userDataFacade.update(dto));
    }

    @Cacheable(value = SEARCH_CACHE_KEY)
    @GetMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SearchUserResult searchWithRq(@Nullable @Valid @RequestBody SearchUserParams rq) {
        return userDataFacade.search(rq);
    }

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
