package com.github.cvazer.tryout.pixelpioneer.service.search;

import com.github.cvazer.tryout.pixelpioneer.service.search.query.BaseUserSearchQueryBuilder;
import com.github.cvazer.tryout.pixelpioneer.service.search.query.UserSearchQueryBuilderChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Set;

@Configuration
public class SearchChainConfig {

    @Bean
    @Primary
    public UserSearchQueryBuilderChain userSearchQueryBuilderChain(Set<BaseUserSearchQueryBuilder> baseBuilders) {
        BaseUserSearchQueryBuilder previous = null;
        BaseUserSearchQueryBuilder first = null;

        for (var current: baseBuilders) {
            if (previous == null) {
                first = current;
            } else {
                previous.setNext(current);
            }
            previous = current;
        }

        return first;
    }

}
