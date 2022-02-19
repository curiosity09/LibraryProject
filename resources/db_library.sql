CREATE SCHEMA library;

SET search_path = library;

CREATE TABLE library.user
(
    id           SERIAL PRIMARY KEY,
    username     CHARACTER VARYING(64) UNIQUE NOT NULL,
    password     CHARACTER VARYING(64)        NOT NULL,
    role         CHARACTER VARYING(10)        NOT NULL,
    first_name   CHARACTER VARYING(64),
    last_name    CHARACTER VARYING(64),
    phone_number CHARACTER VARYING(13),
    email        CHARACTER VARYING(64),
    is_banned    BOOLEAN                      NOT NULL DEFAULT FALSE
);

CREATE TABLE author
(
    id        SERIAL PRIMARY KEY,
    full_name CHARACTER VARYING(64) NOT NULL UNIQUE
);

CREATE TABLE genre
(
    id   SERIAL PRIMARY KEY,
    name CHARACTER VARYING(64) NOT NULL UNIQUE
);

CREATE TABLE section
(
    id   SERIAL PRIMARY KEY,
    name CHARACTER VARYING(64) NOT NULL UNIQUE
);

CREATE TABLE book
(
    id               SERIAL PRIMARY KEY,
    name             CHARACTER VARYING(64)           NOT NULL,
    author_id        INTEGER REFERENCES author (id)  NOT NULL,
    genre_id         INTEGER REFERENCES genre (id)   NOT NULL,
    section_id       INTEGER REFERENCES section (id) NOT NULL,
    quantity         NUMERIC(2)                      NOT NULL,
    publication_year NUMERIC(4)                      NOT NULL
);

CREATE TABLE order_card
(
    id            SERIAL PRIMARY KEY,
    reader_id     INTEGER REFERENCES library.user (id) NOT NULL,
    rental_time   TIMESTAMP                            NOT NULL,
    rental_period TIMESTAMP                            NOT NULL
);

CREATE TABLE order_book
(
    order_id INTEGER REFERENCES order_card (id) NOT NULL,
    book_id  INTEGER REFERENCES book (id)       NOT NULL
);
