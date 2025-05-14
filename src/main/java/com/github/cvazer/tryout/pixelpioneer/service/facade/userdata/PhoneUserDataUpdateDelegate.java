package com.github.cvazer.tryout.pixelpioneer.service.facade.userdata;

import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import com.github.cvazer.tryout.pixelpioneer.api.mapper.PhoneDataMapper;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.PhoneDataEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.repo.PhoneDataRepo;
import com.github.cvazer.tryout.pixelpioneer.service.facade.UserDataIsNotAvailable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class PhoneUserDataUpdateDelegate extends AbstractCollectionUserDataUpdateDelegate<PhoneDataEntity, String> {
    private final PhoneDataMapper mapper;
    private final PhoneDataRepo repo;

    @Override
    protected PhoneDataEntity convertToDataEntity(UserEntity user, String dtoData) {
        return mapper.fromStringView(dtoData, user);
    }

    @Override
    protected Collection<PhoneDataEntity> getMutableEntityCollection(UserEntity user) {
        return user.getPhones();
    }

    @Override
    protected Collection<String> getDtoDataCollection(UserDto dto) {
        return dto.getPhones();
    }

    @Override
    protected String convertToDtoFormat(PhoneDataEntity data) {
        return mapper.stringView(data);
    }

    @Override
    protected void checkAvailability(String data) throws UserDataIsNotAvailable {
        if (repo.existsByValue(data)) throw new UserDataIsNotAvailable("phone", data);
    }
}
