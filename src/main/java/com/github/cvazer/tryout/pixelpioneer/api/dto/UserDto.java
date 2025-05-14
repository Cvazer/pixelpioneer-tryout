package com.github.cvazer.tryout.pixelpioneer.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

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

    private Set<String> emails;
    private Set<String> phones;

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonProperty
    public Double getBalance() {
        return balance;
    }

}
