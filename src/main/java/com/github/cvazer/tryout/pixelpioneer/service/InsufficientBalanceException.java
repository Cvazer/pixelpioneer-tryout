package com.github.cvazer.tryout.pixelpioneer.service;

import com.github.cvazer.tryout.pixelpioneer.api.ApiException;

public class InsufficientBalanceException extends ApiException {
    public static final String TEMPLATE = "User's [%s] balance [%.2f] is lower then required [%.2f] amount.";

    public InsufficientBalanceException(long userId, double balance, double amount) {
        super(String.format(TEMPLATE, userId, balance, amount));
    }

}
