--AUTHORS--
CREATE TABLE authors (
    id BIGINT IDENTITY PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    middle_name VARCHAR(30) NOT NULL
)
INSERT INTO authors (first_name, last_name, middle_name) VALUES ('Клиффорд', 'Саймак', '')
INSERT INTO authors (first_name, last_name, middle_name) VALUES ('Гарри', 'Гаррисон', '')
INSERT INTO authors (first_name, last_name, middle_name) VALUES ('Владимир', 'Цесевич', 'Платонович')
INSERT INTO authors (first_name, last_name, middle_name) VALUES ('Валерий', 'Комягин', 'Борисович')
--AUTHORS--

--GENRES--
CREATE TABLE genres (
    id BIGINT IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL
)
INSERT INTO genres (name) VALUES ('Фантастика')
INSERT INTO genres (name) VALUES ('Астрономия')
INSERT INTO genres (name) VALUES ('Компьютерная литература')
--GENRES--

--BOOKS--
CREATE TABLE books (
    id BIGINT IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    author_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    publisher VARCHAR(10) NOT NULL,
    year SMALLINT NOT NULL,
    city VARCHAR(30) NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors (id),
    FOREIGN KEY (genre_id) REFERENCES genres (id)
)
INSERT INTO books (name, author_id, genre_id, publisher, year, city) VALUES ('Коллекционер', 0, 0, 'Москва', '1990', 'Москва')
INSERT INTO books (name, author_id, genre_id, publisher, year, city) VALUES ('Запад Эдема', 1, 0, 'Питер', '1995', 'Санкт-Петербург')
INSERT INTO books (name, author_id, genre_id, publisher, year, city) VALUES ('Что и как наблюдать на небе', 2, 1, 'Москва', '1970', 'Москва')
INSERT INTO books (name, author_id, genre_id, publisher, year, city) VALUES ('3DS STUDIO', 3, 2, 'Москва', '1992', 'Москва')
--BOOKS--