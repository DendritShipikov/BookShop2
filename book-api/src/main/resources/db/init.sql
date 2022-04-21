create table books (
    id bigserial primary key ,
    title varchar(255),
    author varchar(255),
    user_id bigint,
    count integer
);

create table cart_items (
    book_id bigint references books on DELETE CASCADE,
    user_id bigint,
    book_count integer,
    primary key (book_id, user_id)
);