create table orders (
    id bigserial primary key,
    user_id bigint,
    status integer,
    date timestamp
);

create table order_items (
    order_id bigint references orders,
    book_id bigint,
    book_count int,
    primary key (order_id, book_id)
);