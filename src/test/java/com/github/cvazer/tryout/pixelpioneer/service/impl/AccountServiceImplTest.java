package com.github.cvazer.tryout.pixelpioneer.service.impl;

import com.github.cvazer.tryout.pixelpioneer.dao.entity.AccountEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.repo.AccountRepo;
import com.github.cvazer.tryout.pixelpioneer.security.AuthUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepo accountRepo;

    @Spy
    @InjectMocks
    private AccountServiceImpl service;

    @Test
    public void givenRecipientAndAmount_whenCalled_correctResult() {
        var userAccount = new AccountEntity(BigDecimal.valueOf(1000), 1L, 1L, null);
        var recipientAccount = new AccountEntity(BigDecimal.valueOf(1000), 2L, 2L, null);
        var transferAmount = new BigDecimal(100);

        var expectedUserBalance = userAccount.getBalance()
                .subtract(transferAmount)
                .setScale(2, RoundingMode.HALF_UP);

        var expectedRecipientBalance = recipientAccount.getBalance()
                .add(transferAmount)
                .setScale(2, RoundingMode.HALF_UP);

        doReturn(Optional.of(userAccount)).when(accountRepo).findByUser_Id(userAccount.getUserId());
        doReturn(Optional.of(recipientAccount)).when(accountRepo).findByUser_Id(recipientAccount.getUserId());

        setupAuthStubbingAnd(userAccount.getUserId(),
                () -> service.transfer(recipientAccount.getId(), transferAmount.doubleValue()));

        assertEquals(expectedUserBalance, userAccount.getBalance());
        assertEquals(expectedRecipientBalance, recipientAccount.getBalance());
    }

    public void setupAuthStubbingAnd(long returnedUserId, Runnable action) {
        try (var mockedStatic = mockStatic(AuthUtils.class)) {
            mockedStatic.when(AuthUtils::getUserIdFromSecurityContext).thenReturn(returnedUserId);
            action.run();
        }
    }

    @Test
    public void givenNegativeAmount_whenCalled_exceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> service.transfer(2L, -10));
    }

    @Test
    public void givenUserAndRecipientAreTheSame_whenCalled_correctExceptionThrown() {
        var userId = 1L;
        Executable executable = () -> setupAuthStubbingAnd(userId, () -> service.transfer(userId, 0L));
        assertThrows(IllegalArgumentException.class, executable);
    }

}