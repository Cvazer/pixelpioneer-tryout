package com.github.cvazer.tryout.pixelpioneer.api.controller;

import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.AccountEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.EmailDataEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.PhoneDataEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import com.github.cvazer.tryout.pixelpioneer.dao.repo.UserRepo;
import com.github.cvazer.tryout.pixelpioneer.security.JwtProvider;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class UserUpdateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ObjectMapper mapper = new ObjectMapper();

    @Container
    public static RedisContainer redis = new RedisContainer(DockerImageName.parse("redis:latest"))
            .withExposedPorts(6379);

    @Container
    @SuppressWarnings("resource")
    public static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withUsername("postgres")
            .dependsOn(redis);

    @Test
    public void test() throws Exception {
        var user = createUser("Test1", "password", 1000,
                Set.of("11111111111", "22222222222"), Set.of("test-user-1@example.com"));

        var token = jwtProvider.generateAndSign(user);
        var rq = new UserDto();

        //remove one of the phones
        rq.setPhones(Set.of(user.getPhones().stream().findFirst().orElseThrow().getValue()));

        //add another email
        var newEmail = "another-email@example.com";
        var emails = user.getEmails().stream()
                .map(EmailDataEntity::getValue)
                .collect(Collectors.toSet());
        emails.add(newEmail);
        rq.setEmails(emails);

        mockMvc.perform(
                put("/api/user")
                        .content(mapper.writeValueAsString(rq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, "Bearer "+token.serialize())
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.emails[?(@=='"+newEmail+"')]").exists())
                .andExpect(jsonPath("$.data.phones[0:]", hasSize(1)));
    }

    private UserEntity createUser(
            String name,
            String password,
            double balance,
            Collection<String> phones,
            Collection<String> emails
    ) {

        var user = new UserEntity();
        user.setName(name);
        user.setDateOfBirth(LocalDate.now());
        user.setPassword(passwordEncoder.encode(password));

        user.setEmails(emails.stream()
                .map(it -> new EmailDataEntity(it, null, user))
                .collect(Collectors.toSet()));

        user.setPhones(phones.stream()
                .map(it -> new PhoneDataEntity(it, null, user))
                .collect(Collectors.toSet()));

        var account = new AccountEntity();
        account.setBalance(BigDecimal.valueOf(balance));
        account.setUser(user);
        user.setAccount(account);

        return userRepo.save(user);
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgres.getJdbcUrl());
        registry.add("spring.datasource.username", () -> postgres.getUsername());
        registry.add("spring.datasource.password", () -> postgres.getPassword());
        registry.add("spring.redis.host", () -> redis.getHost());
        registry.add("spring.redis.port", () -> redis.getMappedPort(6379).toString());
    }

}