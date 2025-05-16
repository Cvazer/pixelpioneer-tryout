package com.github.cvazer.tryout.pixelpioneer.service.facade.userdata.update;

import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;

/**
 * Attempts to update user data with values from DTO
 */
public interface UserDataUpdateDelegate {

    void update(UserEntity user, UserDto dto);

}
