package com.github.cvazer.tryout.pixelpioneer.api.controller;

import com.github.cvazer.tryout.pixelpioneer.api.ApiResponse;
import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import com.github.cvazer.tryout.pixelpioneer.service.facade.UserDataFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserDataFacade userDataFacade;

    @GetMapping
    public ApiResponse<UserDto> get() {
        return new ApiResponse<>(userDataFacade.getCurrentUserDto());
    }

}
