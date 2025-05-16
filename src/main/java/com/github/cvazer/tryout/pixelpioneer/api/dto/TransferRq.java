package com.github.cvazer.tryout.pixelpioneer.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRq {

    @Min(1)
    @Schema(example = "2")
    private long recipientId;

    @Min(0)
    @Schema(example = "100.55")
    private double amount;

}
