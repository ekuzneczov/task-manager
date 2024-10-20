--liquibase formatted sql

--changeset kuzneczov:1
CREATE TABLE task_deadline
(
    id            BIGSERIAL PRIMARY KEY,
    task_id       BIGINT                NOT NULL UNIQUE,
    description   VARCHAR(255)          NOT NULL,
    deadline_date TIMESTAMP             NOT NULL,
    user_email    VARCHAR(64)           NOT NULL,
    is_notified   BOOLEAN DEFAULT FALSE NOT NULL
);

--changeset kuzneczov:2
INSERT INTO task_deadline(task_id, description, deadline_date, user_email)
VALUES (1, 'Submit Report', '2024-08-30 17:08:00', 'kuzneczov_ea@mail.ru'),
       (2, 'Complete Project', '2024-08-30 17:10:00', 'kuzneczov_ea@mail.ru'),
       (3, 'Prepare Presentation', '2024-08-30 17:12:00', 'kuzneczov_ea@mail.ru'),
       (4, 'Finalize Budget', '2024-08-30 17:14:00', 'kuzneczov_ea@mail.ru');
