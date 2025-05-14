--liquibase formatted sql
--changeset yan-f:email-data-table

create table email_data (
    id bigint not null primary key generated always as identity,
    user_id bigint not null unique references "user"(id),
    email varchar(200) unique
)