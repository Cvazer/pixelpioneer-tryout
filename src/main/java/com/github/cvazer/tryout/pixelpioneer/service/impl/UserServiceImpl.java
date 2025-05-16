package com.github.cvazer.tryout.pixelpioneer.service.impl;

import com.github.cvazer.tryout.pixelpioneer.api.dto.SearchUserParams;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.repo.UserRepo;
import com.github.cvazer.tryout.pixelpioneer.security.AuthUtils;
import com.github.cvazer.tryout.pixelpioneer.service.UserService;
import com.github.cvazer.tryout.pixelpioneer.service.search.query.UserSearchQueryBuilderChain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo repo;
    private final UserSearchQueryBuilderChain queryBuilderChain;

    @Override
    public UserEntity getCurrentUser() throws IllegalStateException {
        var userId = AuthUtils.getUserIdFromSecurityContext();
        return repo.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Invalid user from JWT claim"));
    }

    @Override
    public Page<UserEntity> search(SearchUserParams rq) {
        Pageable pageable = PageRequest.of(
                rq.getPage(),
                rq.getPageSize(),
                Sort.by(UserEntity.Fields.id).ascending()
        );
        return repo.findAll(queryBuilderChain.build(rq), pageable);
    }

}
