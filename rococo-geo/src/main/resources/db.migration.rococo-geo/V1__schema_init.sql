create extension if not exists "uuid-ossp";

create table if not exists "country"
(
    id   UUID unique        not null default uuid_generate_v1(),
    name varchar(50) unique not null,
    primary key (id)
);

alter table "country"
    owner to postgres;