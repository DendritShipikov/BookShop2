create table profiles (
    id bigserial primary key,
    name varchar(255) unique,
    password varchar(255)
);

create table profile_authorities (
    profile_id bigint references profiles,
    authority varchar(255)
);