package com.github.cvazer.tryout.pixelpioneer.service.impl;

import com.github.cvazer.tryout.pixelpioneer.dao.entity.EmailDataEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.PhoneDataEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.repo.EmailDataRepo;
import com.github.cvazer.tryout.pixelpioneer.dao.repo.PhoneDataRepo;
import com.github.cvazer.tryout.pixelpioneer.security.JwtProvider;
import com.github.cvazer.tryout.pixelpioneer.security.exceptions.BadCredentialsException;
import com.github.cvazer.tryout.pixelpioneer.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final EmailDataRepo emailDataRepo;
    private final PhoneDataRepo phoneDataRepo;
    private final PasswordEncoder encoder;


    @Override
    public UserEntity withPassphrase(String alias, String rawPassphrase) throws BadCredentialsException {
        var userEntity = emailDataRepo.findByValue(alias).map(EmailDataEntity::getUser)
                .or(() -> phoneDataRepo.findByValue(alias).map(PhoneDataEntity::getUser))
                .orElseThrow(BadCredentialsException::new);

        if (!encoder.matches(rawPassphrase, userEntity.getPassword())) {
            throw new BadCredentialsException();
        }

        return userEntity;
    }
}
