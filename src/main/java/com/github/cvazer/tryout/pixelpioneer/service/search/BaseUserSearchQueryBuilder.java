package com.github.cvazer.tryout.pixelpioneer.service.search;

import com.github.cvazer.tryout.pixelpioneer.api.dto.SearchUserRq;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

public abstract class BaseUserSearchQueryBuilder implements UserSearchQueryBuilderChain {

    @Setter
    private BaseUserSearchQueryBuilder next;

    @Override
    public Specification<UserEntity> build(SearchUserRq rq) {
        return buildInternal(null, rq);
    }

    protected Specification<UserEntity> buildInternal(Specification<UserEntity> spec, SearchUserRq rq) {
        var thisSpec = createSpecification(rq);
        if (thisSpec == null) return skip(spec, rq);

        var nextSpec = spec == null ? thisSpec : spec.and(thisSpec);

        if (next != null) {
            return next.buildInternal(nextSpec, rq);
        } else {
            return nextSpec;
        }
    }

    private Specification<UserEntity> skip(Specification<UserEntity> spec, SearchUserRq rq) {
        if (next == null) return spec;
        return next.buildInternal(spec, rq);
    }

    protected abstract Specification<UserEntity> createSpecification(SearchUserRq rq);

}
