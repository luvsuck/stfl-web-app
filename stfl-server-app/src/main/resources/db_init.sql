create schema stfl_app collate utf8mb4_general_ci;

create table user
(
    id       bigint       not null auto_increment primary key,
    username varchar(64)  not null unique,
    password varchar(256) not null
);
