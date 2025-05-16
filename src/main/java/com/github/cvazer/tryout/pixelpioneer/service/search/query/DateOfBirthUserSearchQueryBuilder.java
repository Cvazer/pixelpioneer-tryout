package com.github.cvazer.tryout.pixelpioneer.service.search.query;

import com.github.cvazer.tryout.pixelpioneer.service.model.SearchUserParams;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DateOfBirthUserSearchQueryBuilder extends BaseUserSearchQueryBuilder {

    @Override
    protected Specification<UserEntity> createSpecification(SearchUserParams rq) {
        if (rq.getDateOfBirth() == null) return null;
        return (root, query, cb) ->
                cb.greaterThan(root.get(UserEntity.Fields.dateOfBirth), rq.getDateOfBirth());
    }

}
