package com.github.cvazer.tryout.pixelpioneer.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import static com.github.cvazer.tryout.pixelpioneer.api.ApiResponse.DATE_PATTERN;

@Getter
@Setter
public class UserDto implements Serializable {

    //language=RegExp
    public static final String PHONE_PATTERN = "^[0-9]{0,13}$";

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Double balance;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private LocalDate dateOfBirth;

    @Size(min = 1)
    private Set<@Email String> emails;

    @Size(min = 1)
    private Set<@NotNull @Pattern(regexp = PHONE_PATTERN) String> phones;

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonProperty
    public Double getBalance() {
        return balance;
    }

}
