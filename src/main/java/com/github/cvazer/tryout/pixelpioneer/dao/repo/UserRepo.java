package com.github.cvazer.tryout.pixelpioneer.dao.repo;

import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
}
