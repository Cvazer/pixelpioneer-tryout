package com.github.cvazer.tryout.pixelpioneer.service.facade;

import com.github.cvazer.tryout.pixelpioneer.api.ApiException;
import com.github.cvazer.tryout.pixelpioneer.api.dto.TransferRq;
import com.github.cvazer.tryout.pixelpioneer.api.dto.TransferRs;
import com.github.cvazer.tryout.pixelpioneer.api.mapper.UserMapper;
import com.github.cvazer.tryout.pixelpioneer.dao.repo.UserRepo;
import com.github.cvazer.tryout.pixelpioneer.service.AccountService;
import com.github.cvazer.tryout.pixelpioneer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class AccountFacade {
    private final AccountService accountService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final EntityManager entityManager;

    @Transactional
    public TransferRs transfer(TransferRq rq) {
        var userEntity = userService.getCurrentUser();
        var balance =  userEntity.getAccount().getBalance();

        try {
            accountService.transfer(rq.getRecipientId(), rq.getAmount());
        } catch (IllegalArgumentException e) {
            throw new ApiException(false, 400, e);
        }

        entityManager.refresh(userEntity);
        return new TransferRs(
                userMapper.toDto(userEntity),
                rq.getRecipientId(),
                rq.getAmount(),
                balance.doubleValue()
        );
    }
}
