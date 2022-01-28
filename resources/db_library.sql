CREATE SCHEMA library;

SET search_path = library;

CREATE TABLE library.user
(
    id           SERIAL PRIMARY KEY,
    username     CHARACTER VARYING(64) UNIQUE NOT NULL,
    password     CHARACTER VARYING(64)        NOT NULL,
    role         CHARACTER VARYING(10)        NOT NULL,
    first_name   CHARACTER VARYING(64)        NOT NULL,
    last_name    CHARACTER VARYING(64)        NOT NULL,
    phone_number CHARACTER VARYING(13) UNIQUE NOT NULL,
    email        CHARACTER VARYING(64) UNIQUE,
    is_banned    BOOLEAN                      NOT NULL DEFAULT FALSE
);

CREATE TABLE author
(
    id         SERIAL PRIMARY KEY,
    first_name CHARACTER VARYING(64) NOT NULL,
    last_name  CHARACTER VARYING(64) NOT NULL
);

CREATE TABLE genre
(
    id   SERIAL PRIMARY KEY,
    name CHARACTER VARYING(64) NOT NULL
);

CREATE TABLE section
(
    id   SERIAL PRIMARY KEY,
    name CHARACTER VARYING(64) NOT NULL
);

CREATE TABLE book
(
    id               SERIAL PRIMARY KEY,
    name             CHARACTER VARYING(64) UNIQUE    NOT NULL,
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

INSERT INTO author(first_name, last_name)
VALUES ('Джоан', 'Роулинг'),
       ('Viktor', 'Pelevin'),
       ('Vasya', 'Misha');


INSERT INTO genre(name)
VALUES ('Fantasy'),
       ('Action'),
       ('Roman');

INSERT INTO section(name)
VALUES ('Бестселлеры'),
       ('Научная литература'),
       ('Художественная литература'),
       ('Дестская литература');

INSERT INTO book (name, author_id, genre_id, section_id, quantity, publication_year) (
    VALUES ('Generation P', (SELECT id FROM author WHERE last_name = 'Pelevin'),
            (SELECT id FROM genre WHERE name = 'Action'),
            (SELECT id FROM section WHERE name = 'Художественная литература'), 2, 2004),
           ('Wither', (SELECT id FROM author WHERE last_name = 'Misha'), (SELECT id FROM genre WHERE name = 'Roman'),
            (SELECT id FROM section WHERE name = 'Художественная литература'), 1,
            1993)
);

INSERT INTO library.user(username, password, role, first_name, last_name, phone_number, email)
VALUES ('admin', 'pass', 'admin', 'Dima', 'Basalay', '+37544874224', 'boss@gamil.com'),
       ('login', 'password', 'user', 'Misha', 'Pupkin', '+375445717918', 'misha@gamil.com'),
       ('login1', 'password1', 'user', 'Pasha', 'Aruchko', '+37544787911', 'pasha@gamil.com');

INSERT INTO order_card(reader_id, rental_time, rental_period)
VALUES ((SELECT id FROM library.user WHERE username = 'login1'), now(), '2022-01-30 09:10:47.638545'),
       ((SELECT id FROM library.user WHERE username = 'login'), now(), '2022-02-27 09:10:47.638545');

INSERT INTO order_book (order_id, book_id)
VALUES ((SELECT o.id
         FROM order_card o
                  JOIN library.user u ON o.reader_id = u.id
         WHERE username = 'login'
           AND rental_time = '2022-01-27 11:07:44.897268'),
        (SELECT id FROM book WHERE name = 'Wither'OR id = 2 AND quantity != 0));

UPDATE library.user
SET is_banned = FALSE
WHERE username = 'login';

UPDATE library.book SET quantity = 2 WHERE name = 'Wither';

UPDATE library.book SET author_id = ?, genre_id = ?, section_id = ?, quantity = ?, publication_year = ? WHERE name = ?;

/*DELETE
FROM library.order_card oc
USING library.order_book ob
WHERE ob.order_id = oc.id AND oc.id = 2;*/

SELECT order_id,
       reader_id,
       username,
       role,
       u.first_name AS user_name,
       u.last_name  AS user_surname,
       book_id,
       b.name       AS book_name,
       a.first_name AS author_name,
       a.last_name  AS author_surname,
       g.name       AS genre_name,
       s.name       AS section_name,
       quantity,
       publication_year,
       rental_time,
       rental_period,
       phone_number,
       email
FROM library.order_book ob
         JOIN library.order_card o ON ob.order_id = o.id
         JOIN library.book b ON ob.book_id = b.id
         JOIN library.user u ON o.reader_id = u.id
         JOIN library.author a ON b.author_id = a.id
         JOIN library.genre g ON b.genre_id = g.id
         JOIN library.section s ON b.section_id = s.id;

drop table order_card cascade;
drop table order_book cascade;

drop schema library cascade;

