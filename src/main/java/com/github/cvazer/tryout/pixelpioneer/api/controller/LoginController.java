package com.github.cvazer.tryout.pixelpioneer.api.controller;

import com.github.cvazer.tryout.pixelpioneer.api.ApiResponse;
import com.github.cvazer.tryout.pixelpioneer.api.dto.LogInRq;
import com.github.cvazer.tryout.pixelpioneer.service.facade.LoginFacade;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginFacade loginFacade;

    @Operation(summary = "Provide credentials and receive JWT token")
    @PostMapping
    public ApiResponse<String> login(@Valid @RequestBody LogInRq rq) {
        var token = loginFacade.login(rq.getUsername(), rq.getPassword());
        return new ApiResponse<>(token);
    }

}
