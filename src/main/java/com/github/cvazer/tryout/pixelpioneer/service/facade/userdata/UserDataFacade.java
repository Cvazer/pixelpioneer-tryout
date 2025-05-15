package com.github.cvazer.tryout.pixelpioneer.service.facade.userdata;

import com.github.cvazer.tryout.pixelpioneer.api.dto.SearchUserRq;
import com.github.cvazer.tryout.pixelpioneer.api.dto.SearchUserRs;
import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import com.github.cvazer.tryout.pixelpioneer.api.mapper.UserMapper;
import com.github.cvazer.tryout.pixelpioneer.dao.repo.UserRepo;
import com.github.cvazer.tryout.pixelpioneer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDataFacade {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepo userRepo;

    private final Set<UserDataUpdateDelegate> dataUpdaters;

    @Transactional
    public UserDto getCurrentUserDto() {
        return userMapper.toDto(userService.getCurrentUser());
    }

    @Transactional
    public UserDto update(UserDto dto) {
        var userEntity = userService.getCurrentUser();
        dataUpdaters.forEach(it -> it.update(userEntity, dto));
        return userMapper.toDto(userRepo.save(userEntity));
    }

    @Transactional
    public SearchUserRs search(SearchUserRq rq) {
        var resultPage = userService.search(rq);
        return new SearchUserRs(
                resultPage.getContent().stream()
                        .map(userMapper::toDto)
                        .collect(Collectors.toSet()),
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalPages()
        );
    }
}
