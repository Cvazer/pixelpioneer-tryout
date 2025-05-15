package com.github.cvazer.tryout.pixelpioneer.service.search;


import com.github.cvazer.tryout.pixelpioneer.api.dto.SearchUserRq;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.EmailDataEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class EmailUserSearchQueryBuilder extends BaseUserSearchQueryBuilder {

    @Override
    protected Specification<UserEntity> createSpecification(SearchUserRq rq) {
        if (rq.getEmail() == null) return null;
        return (root, query, cb) ->
                cb.equal(root.join(UserEntity.Fields.emails).get(EmailDataEntity.Fields.value), rq.getEmail());
    }

}
