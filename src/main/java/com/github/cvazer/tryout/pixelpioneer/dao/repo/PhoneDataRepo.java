package com.github.cvazer.tryout.pixelpioneer.dao.repo;

import com.github.cvazer.tryout.pixelpioneer.dao.entity.EmailDataEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.PhoneDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneDataRepo extends JpaRepository<PhoneDataEntity, Long> {

    boolean existsByValue(String value);

    Optional<PhoneDataEntity> findByValue(String value);

}
