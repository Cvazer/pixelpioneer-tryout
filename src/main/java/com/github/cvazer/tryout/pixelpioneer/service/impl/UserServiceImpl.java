package com.github.cvazer.tryout.pixelpioneer.service.impl;

import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.repo.UserRepo;
import com.github.cvazer.tryout.pixelpioneer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo repo;

    @Override
    public UserEntity getCurrentUser() throws IllegalStateException {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new IllegalStateException("Must be called with auth context present");
        }

        if (auth instanceof JwtAuthenticationToken) {
            var jwt = (Jwt) auth.getPrincipal();
            var userId = Long.parseLong(jwt.getSubject());
            return repo.findById(userId)
                    .orElseThrow(() -> new IllegalStateException("Invalid user from JWT claim"));
        } else {
            throw new IllegalStateException(String.format("[%s] auth is not supported", auth.getClass().getName()));
        }
    }
}
