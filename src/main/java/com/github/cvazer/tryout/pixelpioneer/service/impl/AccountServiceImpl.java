package com.github.cvazer.tryout.pixelpioneer.service.impl;

import com.github.cvazer.tryout.pixelpioneer.dao.repo.AccountRepo;
import com.github.cvazer.tryout.pixelpioneer.security.AuthUtils;
import com.github.cvazer.tryout.pixelpioneer.service.AccountService;
import com.github.cvazer.tryout.pixelpioneer.service.InsufficientBalanceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public void transfer(long recipientId, double amount) throws IllegalStateException {

        if (amount < 0) {
            throw new IllegalArgumentException("[amount] must be equal to or greater than zero");
        }

        var bigDecimalAmount = BigDecimal.valueOf(amount).setScale(2, HALF_UP);

        var userId = AuthUtils.getUserIdFromSecurityContext();

        if (userId == recipientId) {
            throw new IllegalArgumentException("Recipient and sender cannot be the same user");
        }

        var userAccount = accountRepo.findByUser_Id(userId)
                .orElseThrow(() -> new IllegalStateException("Unable to find account, user with broken one-to-one dependency"));
        var recipientAccount = accountRepo.findByUser_Id(recipientId)
                .orElseThrow(() -> new IllegalStateException("Unable to find account or user doesn't exist"));

        if (userAccount.getBalance().compareTo(bigDecimalAmount) < 0) {
            throw new InsufficientBalanceException(userId,
                    userAccount.getBalance().doubleValue(), bigDecimalAmount.doubleValue());
        }

        userAccount.setBalance(
                userAccount.getBalance().subtract(bigDecimalAmount)
        );
        recipientAccount.setBalance(
                recipientAccount.getBalance().add(bigDecimalAmount)
        );

        accountRepo.save(userAccount);
        accountRepo.save(recipientAccount);

        log.debug("User [{}] transfers [{}] amount from his account id=[{}] to user's [{}] account [{}]." +
                " New user's balance is [{}] and recipient's balance is [{}]",
                userAccount.getUserId(), bigDecimalAmount, userAccount.getId(),
                recipientAccount.getUserId(), recipientAccount.getId(),
                userAccount.getBalance(), recipientAccount.getBalance());
    }

}
