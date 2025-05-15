package com.github.cvazer.tryout.pixelpioneer.service.search;

import com.github.cvazer.tryout.pixelpioneer.api.dto.SearchUserRq;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UsernameUserSearchQueryBuilder extends BaseUserSearchQueryBuilder {

    @Override
    protected Specification<UserEntity> createSpecification(SearchUserRq rq) {
        if (rq.getName() == null) return null;
        return (root, query, cb) ->
                cb.like(root.get(UserEntity.Fields.name), rq.getName()+"%");
    }
}
