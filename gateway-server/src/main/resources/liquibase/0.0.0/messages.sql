--liquibase formatted sql

--changeset 45532267+kepler-br@users.noreply.github.com:init_tables logicalFilePath:0.0.0/messages.sql
create table messages
(
    id                         bigserial primary key,
    "text"                     varchar null,
    file_url                   varchar(100) null,
    media_type                 varchar(15) null
        constraint chk_messages_media_type check (media_type in
        ('STICKER', 'AUDIO', 'VOICE', 'PHOTO', 'VIDEO',
        'GIF', 'VIDEO_NOTE', 'DOCUMENT', 'ANIMATION')),
    chat_id                    bigint                               not null
        constraint fk_messages_chats_id references gateway.chats (id),
    user_id                    bigint references gateway.users (id) not null
        constraint fk_messages_users_id references gateway.users (id),
    frontend                   varchar(100)                         not null,
    native_id                  varchar(100)                         not null,
    reply_to_message_native_id varchar(100),
    message_date               datetime                             not null,
    created_at                 timestamp null default now(),
    updated_at                 timestamp null default now()
);

create unique index uix_messages_native_id_frontend on messages (native_id, chat_id, user_id, frontend);
