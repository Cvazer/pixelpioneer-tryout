package com.github.cvazer.tryout.pixelpioneer.api.mapper;

import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.AccountEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.EmailDataEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.PhoneDataEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = {
        PhoneDataMapper.class,
        EmailDataMapper.class
})
public interface UserMapper {

//    @AnnotateWith(Transactional.class)
    @Mapping(target = "balance", source = "account", qualifiedByName = "accountToBalance")
    UserDto toDto(UserEntity user);

    Set<String> emailStrings(Set<EmailDataEntity> emails);
    Set<String> phoneStrings(Set<PhoneDataEntity> phones);

    @Named("accountToBalance")
    default Double accountToBalance(AccountEntity account) {
        return account.getBalance().doubleValue();
    }
}
