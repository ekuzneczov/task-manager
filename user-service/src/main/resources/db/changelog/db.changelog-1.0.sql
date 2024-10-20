--liquibase formatted sql

--changeset kuzneczov:1
CREATE TABLE users
(
    id        BIGSERIAL PRIMARY KEY,
    username  VARCHAR(64)  NOT NULL UNIQUE,
    firstname VARCHAR(128) NOT NULL,
    lastname  VARCHAR(128) NOT NULL,
    password  VARCHAR(64)  NOT NULL,
    email     VARCHAR(128) NOT NULL UNIQUE
);