--liquibase formatted sql
--changeset yan-f:account-table

create table account (
    id bigint not null primary key generated always as identity,
    user_id bigint not null unique references "user"(id),
    balance decimal not null default 0
)