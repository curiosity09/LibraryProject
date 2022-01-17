CREATE SCHEMA library;

SET search_path = library;

CREATE TABLE account
(
    id           SERIAL PRIMARY KEY,
    username     CHARACTER VARYING(64) UNIQUE NOT NULL,
    password     CHARACTER VARYING(64)        NOT NULL,
    role         CHARACTER VARYING(10)        NOT NULL,
    first_name   CHARACTER VARYING(64)        NOT NULL,
    last_name    CHARACTER VARYING(64)        NOT NULL,
    phone_number CHARACTER VARYING(13) UNIQUE NOT NULL,
    email        CHARACTER VARYING(64) UNIQUE
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

CREATE TABLE book
(
    id               SERIAL PRIMARY KEY,
    name             CHARACTER VARYING(64)          NOT NULL,
    author_id        INTEGER REFERENCES author (id) NOT NULL,
    genre_id         INTEGER REFERENCES genre (id)  NOT NULL,
    publication_year NUMERIC(4)                     NOT NULL
);

CREATE TABLE order_card
(
    id            SERIAL PRIMARY KEY,
    book_id       INTEGER REFERENCES book (id)    NOT NULL,
    reader_id     INTEGER REFERENCES account (id) NOT NULL,
    rental_time   DATE                            NOT NULL,
    rental_period INTERVAL
);

CREATE TABLE black_list
(
    user_id INTEGER REFERENCES account (id)
);

INSERT INTO author(first_name, last_name)
VALUES ('Джоан','Роулинг'),
       ('Viktor', 'Pelevin'),
       ('Vasya', 'Misha');


INSERT INTO genre(name)
VALUES ('Fantasy'),
       ('Action'),
       ('Roman');

INSERT INTO book (name, author_id, genre_id, publication_year) (
    VALUES ('Generation P', (SELECT id FROM author WHERE last_name = 'Pelevin'),
            (SELECT id FROM genre WHERE name = 'Action'), 2004),
           ('Wither', (SELECT id FROM author WHERE last_name = 'Misha'), (SELECT id FROM genre WHERE name = 'Roman'),
            1993)
);

INSERT INTO account(username, password, role, first_name, last_name, phone_number, email)
VALUES ('login', 'password', 'reader', 'Misha', 'Pupkin', '+375445717918', 'misha@gamil.com'),
       ('login1', 'password1', 'reader', 'Pasha', 'Aruchko', '+37544787911', 'pasha@gamil.com');

INSERT INTO order_card(book_id, reader_id, rental_time, rental_period)
VALUES ((SELECT id FROM book WHERE name = 'Generation P'), (SELECT id FROM account WHERE username = 'login'), '2022-01-07',
        '30 day'),
       ((SELECT id FROM book WHERE name = 'Wither'), (SELECT id FROM account WHERE username = 'login1'), '2022-01-06',
        '10 day');

SELECT a.first_name AS first_name, a.last_name AS last_name
FROM library.author a
         JOIN library.book b ON a.id = b.author_id
GROUP BY a.first_name, a.last_name, b.name
HAVING b.name = 'Wither';

SELECT b.name, b.publication_year, a.first_name, g.name
FROM book b
         JOIN author a ON b.author_id = a.id
         JOIN genre g ON b.genre_id = g.id
GROUP BY b.name, b.publication_year, a.first_name, g.name;

SELECT name,
       u.first_name AS author_name,
       u.last_name  AS author_surname,
       a.first_name AS reader_name,
       a.last_name  AS reader_surname,
       phone_number,
       email,
       rental_time,
       rental_period
FROM library.order_card o
         JOIN library.book b ON o.book_id = b.id
         JOIN library.author u ON b.author_id = u.id
         JOIN library.account a ON o.reader_id = a.id
GROUP BY name, u.first_name, u.last_name, a.first_name, a.last_name, phone_number, email, rental_time, rental_period;

SELECT id,
       username,
       password,
       role,
       first_name,
       last_name,
       phone_number,
       email
FROM library.account;

INSERT INTO library.book (name, author_id, genre_id, publication_year) VALUES ((?), (SELECT id FROM author WHERE last_name = (?) AND first_name = (?)), (SELECT id FROM genre WHERE name = (?)), (?));

drop schema library cascade;

