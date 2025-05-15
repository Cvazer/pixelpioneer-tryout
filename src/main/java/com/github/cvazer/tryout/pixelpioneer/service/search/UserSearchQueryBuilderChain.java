package com.github.cvazer.tryout.pixelpioneer.service.search;

import com.github.cvazer.tryout.pixelpioneer.api.dto.SearchUserRq;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public interface UserSearchQueryBuilderChain {

    Specification<UserEntity> build(SearchUserRq rq);

}
