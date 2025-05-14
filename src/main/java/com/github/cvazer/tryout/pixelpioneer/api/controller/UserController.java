package com.github.cvazer.tryout.pixelpioneer.api.controller;

import com.github.cvazer.tryout.pixelpioneer.api.ApiResponse;
import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import com.github.cvazer.tryout.pixelpioneer.service.facade.userdata.UserDataFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserDataFacade userDataFacade;

    @GetMapping
    public ApiResponse<UserDto> get() {
        return new ApiResponse<>(userDataFacade.getCurrentUserDto());
    }

    @PutMapping
    public ApiResponse<UserDto> update(@RequestBody @Valid UserDto dto)  {
        return new ApiResponse<>(userDataFacade.update(dto));
    }

}
