package com.github.cvazer.tryout.pixelpioneer.service.facade;

import com.github.cvazer.tryout.pixelpioneer.api.ApiException;

public class UserDataIsNotAvailable extends ApiException {
    public UserDataIsNotAvailable(String dataType, String value) {
        super(String.format("%s [%s] is already in use", dataType, value));
    }
}
