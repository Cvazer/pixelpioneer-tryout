package com.github.cvazer.tryout.pixelpioneer.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@RequiredArgsConstructor
public class LogInRq {

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

}
