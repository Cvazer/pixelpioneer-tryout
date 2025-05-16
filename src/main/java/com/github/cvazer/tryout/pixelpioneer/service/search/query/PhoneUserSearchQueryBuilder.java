package com.github.cvazer.tryout.pixelpioneer.service.search.query;


import com.github.cvazer.tryout.pixelpioneer.api.dto.SearchUserParams;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.EmailDataEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PhoneUserSearchQueryBuilder extends BaseUserSearchQueryBuilder {

    @Override
    protected Specification<UserEntity> createSpecification(SearchUserParams rq) {
        if (rq.getPhone() == null) return null;
        return (root, query, cb) ->
                cb.equal(root.join(UserEntity.Fields.phones).get(EmailDataEntity.Fields.value), rq.getPhone());
    }

}
