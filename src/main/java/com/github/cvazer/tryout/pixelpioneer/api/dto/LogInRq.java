package com.github.cvazer.tryout.pixelpioneer.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@RequiredArgsConstructor
public class LogInRq {

    @NotBlank
    @Schema(example = "user1-1@exmaple.com")
    private final String username;

    @NotBlank
    @Schema(example = "password")
    private final String password;

}
