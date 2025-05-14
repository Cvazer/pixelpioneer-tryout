--liquibase formatted sql
--changeset yan-f:user-table

create table "user" (
    id bigint not null primary key generated always as identity,
    name varchar(500) not null,
    date_of_birth date not null,
    password varchar(500) not null check ( char_length(password) >= 8 )
)