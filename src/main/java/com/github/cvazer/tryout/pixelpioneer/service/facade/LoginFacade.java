package com.github.cvazer.tryout.pixelpioneer.service.facade;

 import com.github.cvazer.tryout.pixelpioneer.dao.repo.UserRepo;
import com.github.cvazer.tryout.pixelpioneer.security.JwtProvider;
import com.github.cvazer.tryout.pixelpioneer.security.exceptions.BadCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginFacade {
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    private final UserRepo userRepo;

    public String login(String username, String password) {
        var userEntity = userRepo.findByName(username)
                .orElseThrow(BadCredentialsException::new);

        if (!encoder.matches(password, userEntity.getPassword())) {
            throw new BadCredentialsException();
        }

        return jwtProvider.generateAndSign(userEntity).serialize();
    }
}
