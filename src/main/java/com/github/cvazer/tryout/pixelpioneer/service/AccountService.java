package com.github.cvazer.tryout.pixelpioneer.service;

public interface AccountService {

    // Округляю вверх, так как при банковских операциях обычно так и делают, на сколько мне известно.

    /**
     * <p>Transfers money from current user to specified one</p>
     * <p>Note: given amount will be rounded UP to precision of two decimal places</p>
     * @param recipientId id of user who will receive transfer
     * @param amount how much to transfer. Must equal to 0 or higher;
     * @throws IllegalStateException when called outside of security context or when no user exists with id
     * from user token claim
     * @throws InsufficientBalanceException when user's balance is lower than requested amount
     * @throws IllegalArgumentException when amount is lower than zero or when attempting transfer to yourself
     * {@code recipientId} exists
     */
    void transfer(long recipientId, double amount) throws IllegalStateException,
            InsufficientBalanceException, IllegalArgumentException;

}
