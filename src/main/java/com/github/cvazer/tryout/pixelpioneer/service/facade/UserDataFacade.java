package com.github.cvazer.tryout.pixelpioneer.service.facade;

import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import com.github.cvazer.tryout.pixelpioneer.api.mapper.UserMapper;
import com.github.cvazer.tryout.pixelpioneer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserDataFacade {
    private final UserService userService;
    private final UserMapper mapper;

    @Transactional
    public UserDto getCurrentUserDto() {
        return mapper.toDto(userService.getCurrentUser());
    }

}
