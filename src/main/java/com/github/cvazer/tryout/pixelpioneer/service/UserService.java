package com.github.cvazer.tryout.pixelpioneer.service;

import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;

public interface UserService {

    /**
     * <p>Attempts to fetch user using data in
     * {@link org.springframework.security.core.context.SecurityContextHolder}<p/>
     * @throws IllegalStateException when no security context or authentication exists, or it is not supported
     */
    UserEntity getCurrentUser() throws IllegalStateException;

}
