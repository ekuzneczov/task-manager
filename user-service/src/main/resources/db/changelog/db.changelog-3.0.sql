--liquibase formatted sql

--changeset kuzneczov:1
INSERT INTO authorities (name)
VALUES ('READ'),
       ('WRITE'),
       ('DELETE');

--changeset kuzneczov:2
INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

--changeset kuzneczov:3
INSERT INTO roles_authorities (role_id, authority_id)
SELECT r.id, a.id
FROM roles r, authorities a
WHERE r.name = 'ROLE_USER' AND a.name IN ('READ', 'WRITE');

--changeset kuzneczov:4
INSERT INTO roles_authorities (role_id, authority_id)
SELECT r.id, a.id
FROM roles r, authorities a
WHERE r.name = 'ROLE_ADMIN' AND a.name IN ('READ', 'WRITE', 'DELETE');