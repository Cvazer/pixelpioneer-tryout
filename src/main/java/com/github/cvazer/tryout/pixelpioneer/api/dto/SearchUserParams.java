package com.github.cvazer.tryout.pixelpioneer.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static com.github.cvazer.tryout.pixelpioneer.api.ApiResponse.DATE_PATTERN;
import static com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto.PHONE_PATTERN;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserParams {
    public static final int DEFAULT_PAGE_SIZE = 10;

    @Min(0)
    @Schema(example = "1")
    private int page;

    @Min(0)
    @Max(100)
    @Schema(example = "10")
    private int pageSize;

    @Schema(example = "User")
    @Pattern(regexp = "^[^%\\s]{1,500}$")
    private String name;

    @Schema(example = "1997.01.01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private LocalDate dateOfBirth;

    @Email
    @Schema(example = "user1-1@exmaple.com")
    private String email;

    @Schema(example = "73955956735")
    @Pattern(regexp = PHONE_PATTERN)
    private String phone;

}
