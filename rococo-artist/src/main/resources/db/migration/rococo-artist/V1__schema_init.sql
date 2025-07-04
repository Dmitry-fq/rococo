-- create database "rococo-artist" with owner postgres;

create extension if not exists "uuid-ossp";

create table if not exists "artist"
(
    id        UUID unique        not null default uuid_generate_v1(),
    name      varchar(50) unique not null,
    biography varchar(255),
    photo     bytea,
    primary key (id)
);

alter table "artist"
    owner to postgres;