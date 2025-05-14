--liquibase formatted sql
--changeset yan-f:phone-data-table

create table phone_data (
    id bigint not null primary key generated always as identity,
    user_id bigint not null unique references "user"(id),
    phone varchar(13) unique
)