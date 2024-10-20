--liquibase formatted sql

--changeset kuzneczov:1
CREATE TABLE roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

--changeset kuzneczov:2
CREATE TABLE authorities
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

--changeset kuzneczov:3
CREATE TABLE roles_authorities
(
    role_id        BIGINT NOT NULL,
    authority_id  BIGINT NOT NULL,
    PRIMARY KEY (role_id, authority_id),
    CONSTRAINT fk_roles FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT fk_authorities FOREIGN KEY (authority_id) REFERENCES authorities (id)
);

--changeset kuzneczov:4
CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_roles FOREIGN KEY (role_id) REFERENCES roles (id)
);