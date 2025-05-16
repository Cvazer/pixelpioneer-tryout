package com.github.cvazer.tryout.pixelpioneer.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private int page;

    @Min(0)
    @Max(100)
    private int pageSize;

    @Pattern(regexp = "^[^%\\s]{1,500}$")
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private LocalDate dateOfBirth;

    @Email
    private String email;

    @Pattern(regexp = PHONE_PATTERN)
    private String phone;

}
