package com.github.cvazer.tryout.pixelpioneer.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class AuthUtils {

    public static long getUserIdFromSecurityContext() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new IllegalStateException("Must be called with auth context present");
        }

        if (auth instanceof JwtAuthenticationToken) {
            var jwt = (Jwt) auth.getPrincipal();
            return Long.parseLong(jwt.getSubject());
        } else {
            throw new IllegalStateException(String.format("[%s] auth is not supported", auth.getClass().getName()));
        }
    }

}
