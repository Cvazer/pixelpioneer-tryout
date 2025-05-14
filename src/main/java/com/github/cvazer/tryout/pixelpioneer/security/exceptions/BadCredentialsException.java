package com.github.cvazer.tryout.pixelpioneer.security.exceptions;

import com.github.cvazer.tryout.pixelpioneer.api.ApiException;

public class BadCredentialsException extends ApiException {
    public BadCredentialsException() {
        super("User missing or incorrect password");
    }
}
