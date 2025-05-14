package com.github.cvazer.tryout.pixelpioneer.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`user`")
public class UserEntity {
    private String name;
    private LocalDate dateOfBirth;
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = ALL)
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private AccountEntity account;

    @OneToMany(cascade = ALL, orphanRemoval = true, mappedBy = "user")
    private Set<EmailDataEntity> emails;

    @OneToMany(cascade = ALL, orphanRemoval = true, mappedBy = "user")
    private Set<PhoneDataEntity> phones;
}
