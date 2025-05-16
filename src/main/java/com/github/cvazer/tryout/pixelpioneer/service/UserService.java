package com.github.cvazer.tryout.pixelpioneer.service;

import com.github.cvazer.tryout.pixelpioneer.api.dto.SearchUserParams;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import org.springframework.data.domain.Page;

public interface UserService {

    /**
     * <p>Attempts to fetch user using data in
     * {@link org.springframework.security.core.context.SecurityContextHolder}<p/>
     * @throws IllegalStateException when no security context or authentication exists, or it is not supported
     */
    UserEntity getCurrentUser() throws IllegalStateException;

    /**
     * @return {@link org.springframework.data.domain.Page} that contains entities that
     * correspond to given parameters
     */
    Page<UserEntity> search(SearchUserParams rq);
}
