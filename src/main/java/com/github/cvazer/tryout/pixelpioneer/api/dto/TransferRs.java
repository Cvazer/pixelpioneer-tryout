package com.github.cvazer.tryout.pixelpioneer.api.dto;

import com.github.cvazer.tryout.pixelpioneer.api.ApiResponse;
import lombok.Getter;

@Getter
public class TransferRs extends ApiResponse<UserDto> {

    private final long recipientId;
    private final double amount;
    private final double priorBalance;

    public TransferRs(UserDto data, long recipientId, double amount, double priorBalance) {
        super(data);
        this.recipientId = recipientId;
        this.amount = amount;
        this.priorBalance = priorBalance;
    }
}
