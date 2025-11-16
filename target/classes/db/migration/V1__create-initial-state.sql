create table authors(
    id bigint not null auto_increment,

    name varchar(255) not null,

    constraint pk_authors_id primary key(id),
    constraint uidx_authors_name unique(name)
);

create table books(
    id bigint not null auto_increment,
    author_id bigint not null,
    title varchar(255) not null,
    stock bigint not null,
    deleted boolean not null default false,

    constraint pk_books_id primary key(id),

    constraint fk_books_author_id
        foreign key(author_id) 
        references authors(id)
        on delete cascade,

    constraint chk_books_stock_positive check (stock >= 0),
    index idx_books_author_id (author_id)
);