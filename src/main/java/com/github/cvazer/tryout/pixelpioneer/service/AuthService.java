package com.github.cvazer.tryout.pixelpioneer.service;

import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import com.github.cvazer.tryout.pixelpioneer.security.exceptions.BadCredentialsException;

public interface AuthService {

    /**
     * <p>Attempts to authenticate user via provided alias and a passphrase (basic auth)</p>
     * <p>Will attempt alias lookup in multiple sources  ({@code phone} and {@code email})</p>
     * @param alias that the passphrase will be tested against. Must be a unique user data ({@code phone} and {@code email}
     * @param rawPassphrase not encoded or otherwise armored user's passphrase
     * @return {@link UserEntity} representing user
     * @throws BadCredentialsException if user does not exist, or provided passphrase and alias pair is not matching
     */
    UserEntity withPassphrase(String alias, String rawPassphrase) throws BadCredentialsException;

}
