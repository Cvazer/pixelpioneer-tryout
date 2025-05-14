package com.github.cvazer.tryout.pixelpioneer.security.exceptions;


import com.github.cvazer.tryout.pixelpioneer.api.ApiException;

import java.nio.file.Path;
import java.text.ParseException;

public class JwkException extends ApiException {

    public JwkException(Path jwkPath, ParseException e) {
        super(String.format("Unable to load JWK from path [%s]", jwkPath), e);
    }
}
