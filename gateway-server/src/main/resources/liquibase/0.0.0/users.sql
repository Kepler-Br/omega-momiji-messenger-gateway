--liquibase formatted sql

--changeset 45532267+kepler-br@users.noreply.github.com:init_tables logicalFilePath:0.0.0/users.sql
create table users
(
    id         bigserial primary key,
    username   varchar(1024),
    first_name varchar(1024),
    last_name  varchar(1024),
    frontend   varchar(100) not null,
    native_id  varchar(100) not null,
    created_at timestamp null default now(),
    updated_at timestamp null default now()
);

create unique index uix_users_native_id_frontend on users (native_id, frontend);
