package com.github.cvazer.tryout.pixelpioneer.api.dto;

import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRq {

    @Min(1)
    private long recipientId;

    @Min(0)
        private double amount;

}
