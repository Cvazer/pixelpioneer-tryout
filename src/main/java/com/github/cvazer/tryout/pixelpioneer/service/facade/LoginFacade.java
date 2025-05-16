package com.github.cvazer.tryout.pixelpioneer.service.facade;

import com.github.cvazer.tryout.pixelpioneer.security.JwtProvider;
import com.github.cvazer.tryout.pixelpioneer.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginFacade {
    private final AuthService authService;
    private final JwtProvider jwtProvider;

    public String login(String username, String password) {
        var userEntity = authService.withPassphrase(username, password);
        return jwtProvider.generateAndSign(userEntity).serialize();
    }
}
