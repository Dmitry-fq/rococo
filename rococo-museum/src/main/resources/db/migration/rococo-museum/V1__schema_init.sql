create extension if not exists "uuid-ossp";

create table if not exists "museum"
(
    id          UUID unique not null default uuid_generate_v1(),
    title       varchar(50),
    description varchar(255),
    photo       bytea,
    country_id  UUID        not null,
    city        varchar(50) not null,
    primary key (id)
);

alter table "museum"
    owner to postgres;
