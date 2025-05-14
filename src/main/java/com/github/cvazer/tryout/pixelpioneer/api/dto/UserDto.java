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
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Double balance;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate dateOfBirth;

    @Size(min = 1)
    private Set<@Email String> emails;

    @Size(min = 1)
    private Set<@NotNull @Pattern(regexp = "^\\+375[0-9]{9}$") String> phones;

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonProperty
    public Double getBalance() {
        return balance;
    }

}
