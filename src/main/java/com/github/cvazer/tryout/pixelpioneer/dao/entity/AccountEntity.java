package com.github.cvazer.tryout.pixelpioneer.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

 import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@Cacheable
@Cache(region = "account_entity", usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccountEntity {
    private BigDecimal balance;

    @Column(insertable = false, updatable = false)
    private long userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "account")
    private UserEntity user;
}
