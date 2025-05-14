package com.github.cvazer.tryout.pixelpioneer.dao.repo;

import com.github.cvazer.tryout.pixelpioneer.dao.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, Long> {
}
