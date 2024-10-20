--liquibase formatted sql

--changeset kuzneczov:1
CREATE TABLE project
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) UNIQUE NOT NULL,
    updated_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--changeset kuzneczov:2
CREATE TABLE task_state
(
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(255) NOT NULL,
    project_id          BIGINT REFERENCES project (id),
    left_task_state_id  BIGINT REFERENCES task_state (id),
    right_task_state_id BIGINT REFERENCES task_state (id),
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--changeset kuzneczov:3
CREATE TABLE task
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    task_state_id BIGINT REFERENCES task_state (id),
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description   TEXT
);
