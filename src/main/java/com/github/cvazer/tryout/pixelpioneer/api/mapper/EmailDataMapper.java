package com.github.cvazer.tryout.pixelpioneer.api.mapper;

import com.github.cvazer.tryout.pixelpioneer.dao.entity.EmailDataEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EmailDataMapper {

    default String stringView(EmailDataEntity data) {
        return data.getValue();
    }

}
