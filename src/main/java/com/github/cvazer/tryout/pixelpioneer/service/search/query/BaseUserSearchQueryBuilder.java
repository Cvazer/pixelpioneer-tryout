package com.github.cvazer.tryout.pixelpioneer.service.search.query;

import com.github.cvazer.tryout.pixelpioneer.api.dto.SearchUserParams;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

public abstract class BaseUserSearchQueryBuilder implements UserSearchQueryBuilderChain {

    @Setter
    private BaseUserSearchQueryBuilder next;

    @Override
    public Specification<UserEntity> build(SearchUserParams rq) {
        return buildInternal(null, rq);
    }

    protected Specification<UserEntity> buildInternal(Specification<UserEntity> spec, SearchUserParams rq) {
        var thisSpec = createSpecification(rq);
        if (thisSpec == null) return skip(spec, rq);

        var nextSpec = spec == null ? thisSpec : spec.and(thisSpec);

        if (next != null) {
            return next.buildInternal(nextSpec, rq);
        } else {
            return nextSpec;
        }
    }

    private Specification<UserEntity> skip(Specification<UserEntity> spec, SearchUserParams rq) {
        if (next == null) return spec;
        return next.buildInternal(spec, rq);
    }

    protected abstract Specification<UserEntity> createSpecification(SearchUserParams rq);

}
