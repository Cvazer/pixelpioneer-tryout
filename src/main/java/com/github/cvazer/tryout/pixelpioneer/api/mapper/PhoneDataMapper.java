package com.github.cvazer.tryout.pixelpioneer.api.mapper;

import com.github.cvazer.tryout.pixelpioneer.dao.entity.PhoneDataEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PhoneDataMapper {

    default String stringView(PhoneDataEntity data) {
        return data.getValue();
    }

}
