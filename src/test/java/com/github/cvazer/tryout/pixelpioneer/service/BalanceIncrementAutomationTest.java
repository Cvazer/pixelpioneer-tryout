package com.github.cvazer.tryout.pixelpioneer.service;

import com.github.cvazer.tryout.pixelpioneer.dao.entity.AccountEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.repo.AccountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import static com.github.cvazer.tryout.pixelpioneer.service.BalanceIncrementAutomation.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BalanceIncrementAutomationTest {

    @Mock
    private RedisTemplate<String, Object> template;

    @Mock
    private ValueOperations<String, Object> valueOps;

    @Mock
    private HashOperations<String, String, Object> hashOps;

    @Mock
    private AccountRepo accountRepo;

    @Spy
    @InjectMocks
    private BalanceIncrementAutomation service;

    @BeforeEach
    public void setupTemplate() {
        doReturn(valueOps).when(template).opsForValue();
        doReturn(hashOps).when(template).opsForHash();
    }

    @Test
    public void givenUsersAndRightTime_whenCalledAndEveryoneCanBeSubsidised_balanceUpdated() {
        var initialBalance = 1000;
        var account = new AccountEntity(BigDecimal.valueOf(initialBalance), 1L, 1L, null);

        setRedisValue(LAST_RUN_KEY, LocalDateTime.now().minusMinutes(1L));
        setRedisHashValue(INITIAL_BALANCE_KEY, account.getId(), BigDecimal.valueOf(initialBalance));

        doReturn(List.of(account)).when(accountRepo).findAll();

        var expectedBalance = account.getBalance().multiply(
                BigDecimal.valueOf(SUBSIDIES_FACTOR).add(BigDecimal.ONE)
        ).setScale(2, RoundingMode.HALF_UP);

        service.update();

        assertEquals(expectedBalance, account.getBalance());
    }

    private void setRedisValue(String key, Object value) {
        doReturn(value).when(valueOps).get(key);
    }

    private void setRedisHashValue(String hashKey, Object key, Object value) {
        doReturn(value).when(hashOps).get(hashKey, key);
    }

}