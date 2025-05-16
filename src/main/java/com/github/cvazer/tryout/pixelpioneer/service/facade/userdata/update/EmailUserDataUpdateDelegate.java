package com.github.cvazer.tryout.pixelpioneer.service.facade.userdata.update;

import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import com.github.cvazer.tryout.pixelpioneer.api.mapper.EmailDataMapper;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.EmailDataEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.repo.EmailDataRepo;
import com.github.cvazer.tryout.pixelpioneer.service.facade.userdata.UserDataIsNotAvailable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class EmailUserDataUpdateDelegate extends AbstractCollectionUserDataUpdateDelegate<EmailDataEntity, String> {
    private final EmailDataMapper mapper;
    private final EmailDataRepo repo;

    @Override
    protected EmailDataEntity convertToDataEntity(UserEntity user, String dtoData) {
        return mapper.fromStringView(dtoData, user);
    }

    @Override
    protected Collection<EmailDataEntity> getMutableEntityCollection(UserEntity user) {
        return user.getEmails();
    }

    @Override
    protected Collection<String> getDtoDataCollection(UserDto dto) {
        return dto.getEmails();
    }

    @Override
    protected String convertToDtoFormat(EmailDataEntity data) {
        return mapper.stringView(data);
    }

    @Override
    protected void checkAvailability(String data) throws UserDataIsNotAvailable {
        if (repo.existsByValue(data)) throw new UserDataIsNotAvailable("email", data);
    }
}
