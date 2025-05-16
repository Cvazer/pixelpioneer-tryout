package com.github.cvazer.tryout.pixelpioneer.dao.repo;

import com.github.cvazer.tryout.pixelpioneer.dao.entity.EmailDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailDataRepo extends JpaRepository<EmailDataEntity, Long> {

    boolean existsByValue(String value);

    Optional<EmailDataEntity> findByValue(String value);

}
