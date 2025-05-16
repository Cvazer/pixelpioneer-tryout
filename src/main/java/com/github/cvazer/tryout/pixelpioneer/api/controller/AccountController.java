package com.github.cvazer.tryout.pixelpioneer.api.controller;

import com.github.cvazer.tryout.pixelpioneer.api.dto.TransferRq;
import com.github.cvazer.tryout.pixelpioneer.api.dto.TransferRs;
import com.github.cvazer.tryout.pixelpioneer.service.facade.AccountFacade;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.github.cvazer.tryout.pixelpioneer.api.controller.UserController.SEARCH_CACHE_KEY;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountFacade accountFacade;

    @PostMapping
    @CacheEvict(value = SEARCH_CACHE_KEY, allEntries = true)
    @Operation(summary = "Transfer money from your account to anther user")
    public TransferRs transfer(@Valid @RequestBody TransferRq rq) {
        return accountFacade.transfer(rq);
    }

}
