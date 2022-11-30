-- liquibase formatted sql

-- changeset Korolenko Sergey:1
CREATE TABLE post
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR,
    description TEXT,
    visible     BOOLEAN,
    city_id     INT,
    created     TIMESTAMP
);
