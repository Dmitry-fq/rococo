create extension if not exists "uuid-ossp";

create table if not exists "painting"
(
    id          UUID unique not null default uuid_generate_v1(),
    artist_id   UUID,
    museum_id   UUID,
    content     bytea,
    title       varchar(50),
    description varchar(255),

    primary key (id)
);

alter table "painting"
    owner to postgres;
