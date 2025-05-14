package com.github.cvazer.tryout.pixelpioneer.api.mapper;

import com.github.cvazer.tryout.pixelpioneer.dao.entity.EmailDataEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EmailDataMapper {

    default String stringView(EmailDataEntity data) {
        return data.getValue();
    }

    @Mapping(target = "value", source = "data")
    @Mapping(target = "user", expression = "java( user )")
    @Mapping(target = "id", ignore = true)
    EmailDataEntity fromStringView(String data, @Context UserEntity user);

}
