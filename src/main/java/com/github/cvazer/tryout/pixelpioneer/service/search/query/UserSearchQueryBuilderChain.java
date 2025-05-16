package com.github.cvazer.tryout.pixelpioneer.service.search.query;

import com.github.cvazer.tryout.pixelpioneer.service.model.SearchUserParams;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public interface UserSearchQueryBuilderChain {

    Specification<UserEntity> build(SearchUserParams rq);

}
