package com.github.cvazer.tryout.pixelpioneer.security;

import com.nimbusds.jose.jwk.RSAKey;

public interface SecurityKeyProvider {

    /**
     * <p>Loads (or generates  if necessary) RSA asymmetric cryptographic key pair from file</p>
     * @return {@link RSAKey} object representing key pair
     */
    RSAKey getRsaKey();

}
