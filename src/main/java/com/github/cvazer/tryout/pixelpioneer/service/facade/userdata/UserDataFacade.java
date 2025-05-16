package com.github.cvazer.tryout.pixelpioneer.service.facade.userdata;

import com.github.cvazer.tryout.pixelpioneer.api.DateParsingException;
import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import com.github.cvazer.tryout.pixelpioneer.api.mapper.UserMapper;
import com.github.cvazer.tryout.pixelpioneer.dao.repo.UserRepo;
import com.github.cvazer.tryout.pixelpioneer.service.UserService;
import com.github.cvazer.tryout.pixelpioneer.service.facade.userdata.update.UserDataUpdateDelegate;
import com.github.cvazer.tryout.pixelpioneer.service.model.SearchUserParams;
import com.github.cvazer.tryout.pixelpioneer.service.model.SearchUserResult;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.cvazer.tryout.pixelpioneer.api.ApiResponse.DATE_PATTERN;
import static com.github.cvazer.tryout.pixelpioneer.service.model.SearchUserParams.DEFAULT_PAGE_SIZE;

@Component
@RequiredArgsConstructor
public class UserDataFacade {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepo userRepo;
    private final ValidatorFactory validatorFactory;

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
    public SearchUserResult search(SearchUserParams rq) {
        var resultPage = userService.search(rq);
        return new SearchUserResult(
                resultPage.getContent().stream()
                        .map(userMapper::toDto)
                        .collect(Collectors.toList()),
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalPages()
        );
    }

    @Transactional
    public SearchUserResult search(
            @Nullable Integer page,
            @Nullable Integer pageSize,
            @Nullable String name,
            @Nullable String dateOfBirth,
            @Nullable String email,
            @Nullable String phone
    ) {

        LocalDate dob;

        try {
            dob = Optional.ofNullable(dateOfBirth)
                    .map(it -> LocalDate.parse(it, DateTimeFormatter.ofPattern(DATE_PATTERN)))
                    .orElse(null);
        } catch (DateTimeParseException e) {
            throw new DateParsingException(dateOfBirth, DATE_PATTERN);
        }

        var rq = new SearchUserParams(
                Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(pageSize).orElse(DEFAULT_PAGE_SIZE),
                name, dob, email, phone
        );

        var violations = validatorFactory.getValidator().validate(rq);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return search(rq);
    }
}
