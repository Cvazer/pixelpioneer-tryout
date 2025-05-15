package com.github.cvazer.tryout.pixelpioneer.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

import static com.github.cvazer.tryout.pixelpioneer.api.controller.UserController.SEARCH_CACHE_KEY;
import static org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;

@EnableCaching
@Configuration
public class SpringCacheConfig extends CachingConfigurerSupport {

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManagerBuilder
                .fromConnectionFactory(connectionFactory)
                .withCacheConfiguration(SEARCH_CACHE_KEY, RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(1)))
                .build();
    }

}
