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
    price decimal(10, 2) not null,
    page_count int not null,
    stock int not null,
    deleted boolean not null default false,

    constraint uq_book_title_author unique(title, author_id),
    constraint pk_books_id primary key(id),
    constraint chk_books_stock_positive check (stock >= 0),
    constraint chk_books_pages_positive check (page_count > 0),
    constraint chk_books_price_positive check (price > 0),
    constraint fk_books_author_id foreign key(author_id) references authors(id),

    index idx_books_author_id (author_id)
);

create table users(
    id bigint not null auto_increment,
    name varchar(255) not null,
    login varchar(255) not null,
    password varchar(255) not null,

    constraint pk_users_id primary key(id),
    constraint uidx_users_login unique(login)
);