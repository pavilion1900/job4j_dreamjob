-- liquibase formatted sql

-- changeset Korolenko Sergey:1
CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR UNIQUE,
    password TEXT
);
