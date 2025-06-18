create extension if not exists "uuid-ossp";

create table if not exists "museum"
(
    id          UUID unique not null default uuid_generate_v1(),
    title       varchar(50),
    description varchar(255),
    photo       bytea,
    country_id  UUID,
    country     varchar(50),
    city        varchar(50),
    primary key (id)
);

alter table "museum"
    owner to postgres;
