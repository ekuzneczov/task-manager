--liquibase formatted sql

--changeset kuzneczov:1
CREATE TABLE task_time
(
    id         BIGSERIAL PRIMARY KEY,
    task_id    BIGINT    NOT NULL UNIQUE,
    start_time TIMESTAMP NOT NULL,
    end_time   TIMESTAMP
);