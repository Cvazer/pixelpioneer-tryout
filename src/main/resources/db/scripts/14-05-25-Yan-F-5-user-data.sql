--liquibase formatted sql
--changeset yan-f:user-data

insert into "user"(name, date_of_birth, password)
values ('User1', to_date('1995-06-06', 'YYYY-MM-DD'), '$2a$10$HtngPqPFEzu.GSbIlkuQaeWj1KW7u0rmfcC/fv6ONuniJItuCOeDC'),
       ('User2', to_date('1998-08-12', 'YYYY-MM-DD'), '$2a$10$HtngPqPFEzu.GSbIlkuQaeWj1KW7u0rmfcC/fv6ONuniJItuCOeDC'),
       ('User3', to_date('1996-06-22', 'YYYY-MM-DD'), '$2a$10$HtngPqPFEzu.GSbIlkuQaeWj1KW7u0rmfcC/fv6ONuniJItuCOeDC');

insert into account(user_id, balance)
values (1, 1000),
       (2, 900),
       (3, 1100);

insert into email_data(user_id, email)
values (1, 'user1-1@exmaple.com'),
       (1, 'user1-2@exmaple.com');

insert into email_data(user_id, email)
values (2, 'user2-1@exmaple.com');

insert into email_data(user_id, email)
values (3, 'user3-1@exmaple.com');

insert into phone_data(user_id, phone)
values (1, '+375252020327'),
       (1, '+375259009910');

insert into phone_data(user_id, phone)
values (2, '+375291002030');

insert into phone_data(user_id, phone)
values (3, '+375444005060');