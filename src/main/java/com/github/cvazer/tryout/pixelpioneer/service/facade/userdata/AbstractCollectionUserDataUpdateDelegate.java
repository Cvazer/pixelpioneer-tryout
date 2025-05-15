package com.github.cvazer.tryout.pixelpioneer.service.facade.userdata;

import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * <p>Adds new and removes old elements of {@link UserEntity} data collection based on intersection with
 * corresponding elements in given {@link UserDto} representation.</p>
 * @param <T> persistence entity type for collection elements
 * @param <K> dto representation type for collection elements
 */
public abstract class AbstractCollectionUserDataUpdateDelegate<T, K> extends AbstractUserDataUpdateDelegate {

    @Override
    public void update(UserEntity user, UserDto dto) {
        var mutableSource = getMutableEntityCollection(user);

        var diff = elementDifference(
                mutableSource.stream()
                        .map(this::convertToDtoFormat)
                        .collect(Collectors.toSet()),
                getDtoDataCollection(dto)
        );
        mutableSource.removeIf(it -> diff.getRight().contains(convertToDtoFormat(it)));
        diff.getLeft().forEach(it -> {
            checkAvailability(it);
            mutableSource.add(convertToDataEntity(user, it));
        });
    }

    /**
     * <p>Converts data from dto to entity format</p>
     * @param user that will be updated with given data
     * @param dtoData dto representation of given data
     * @return entity data converted from dto format
     */
    protected abstract T convertToDataEntity(UserEntity user, K dtoData);

    /**
     * @param user from which update would happen
     * @return mutable collection that will be changed during update
     */
    protected abstract Collection<T> getMutableEntityCollection(UserEntity user);

    /**
     * @param dto representing changed data
     * @return collection extracted from dto that contains not converted changed data
     */
    protected abstract Collection<K> getDtoDataCollection(UserDto dto);

    /**
     * @param data entity to be converted
     * @return entity data in dto format
     */
    protected abstract K convertToDtoFormat(T data);

    /**
     * <p>Checks if given data in dto format is available (not used by  other users) if necessary</p>
     * <p>Should throe {@link UserDataIsNotAvailable} exception in case if data can't be used</p>
     * @param data to be checked
     * @throws UserDataIsNotAvailable when given  data is deemed not available
     */
    protected abstract void checkAvailability(K data) throws UserDataIsNotAvailable;
}
