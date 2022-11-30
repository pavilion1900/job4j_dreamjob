-- liquibase formatted sql

-- changeset Korolenko Sergey:1
CREATE TABLE candidate
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR,
    description TEXT,
    visible     BOOLEAN,
    city_id     INT,
    photo       bytea,
    created     TIMESTAMP
);
