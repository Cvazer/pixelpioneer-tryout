package com.github.cvazer.tryout.pixelpioneer.security;

import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import com.nimbusds.jwt.SignedJWT;

public interface JwtProvider {

    /**
     * <p>Generates JWT token for given {@link UserEntity}</p>
     * @param user for whom jwt will be generated
     * @return {@link SignedJWT} object representing generated  JWT
     */
    SignedJWT generateAndSign(UserEntity user);

}
