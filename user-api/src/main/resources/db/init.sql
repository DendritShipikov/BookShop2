create table users (
    id bigserial primary key,
    username varchar(255) unique,
    password varchar(255)
);

create table user_roles (
    user_id bigint references users,
    role varchar(255)
);