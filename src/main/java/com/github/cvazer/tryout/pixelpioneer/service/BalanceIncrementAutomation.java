package com.github.cvazer.tryout.pixelpioneer.service;

import com.github.cvazer.tryout.pixelpioneer.dao.repo.AccountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.github.cvazer.tryout.pixelpioneer.api.controller.UserController.SEARCH_CACHE_KEY;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceIncrementAutomation {
    public static final String INITIAL_BALANCE_KEY = "initial_balance";
    public static final String LAST_RUN_KEY = "last_run";
     public static final double SUBSIDIES_FACTOR = 0.10;
    public static final double SUBSIDIES_THRESHOLD_FACTOR = 2.07;

    private final AccountRepo accountRepo;
    private final RedisTemplate<String, Object> template;

    @Transactional
    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    @CacheEvict(value = SEARCH_CACHE_KEY, allEntries = true)
    public void update() {
        log.trace("Auto increment account balance is running now");
        LocalDateTime lastRun = (LocalDateTime) template.opsForValue().get(LAST_RUN_KEY);
        var now = LocalDateTime.now();

        if (lastRun == null) {
            template.opsForValue().set(LAST_RUN_KEY, now);
            lastRun = now;
        }

        if (lastRun.plusSeconds(30).isAfter(now)) {
            log.trace("Too soon, Aborting auto increment");
            return;
        }

        template.opsForValue().set(LAST_RUN_KEY, now);

        accountRepo.findAll().forEach(account -> {
            if (!template.opsForHash().hasKey(INITIAL_BALANCE_KEY, account.getId())) {
                template.opsForHash().put(INITIAL_BALANCE_KEY, account.getId(), account.getBalance());
                log.debug("Recording user's [{}] initial balance [{}]", account.getUserId(), account.getBalance());
            }

            var initialBalance = (BigDecimal) Objects
                    .requireNonNull(template.opsForHash().get(INITIAL_BALANCE_KEY, account.getId()));
            initialBalance = initialBalance.setScale(2, RoundingMode.HALF_UP);

            var amount = account.getBalance().multiply(BigDecimal.valueOf(SUBSIDIES_FACTOR))
                    .setScale(2, RoundingMode.HALF_UP);
            var mostPossibleAmount = initialBalance.multiply(BigDecimal.valueOf(SUBSIDIES_THRESHOLD_FACTOR))
                    .setScale(2, RoundingMode.HALF_UP);
            var newBalance = account.getBalance().add(amount)
                    .setScale(2, RoundingMode.HALF_UP);

            if (newBalance.compareTo(mostPossibleAmount) > 0) {
                log.debug("User's [{}] balance [{}] reached it's \"no more then 207%\" [{}] of initial [{}] balance",
                        account.getUserId(), account.getBalance(), mostPossibleAmount, initialBalance);
                return;
            }

            log.debug("Auto. increase user [{}] balance [{}]. initial=[{}] mostPossible=[{}] amount=[{}] newBalance=[{}]",
                    account.getUserId(), account.getBalance(), initialBalance, mostPossibleAmount, amount, newBalance);

            account.setBalance(newBalance);
        });
    }
}
