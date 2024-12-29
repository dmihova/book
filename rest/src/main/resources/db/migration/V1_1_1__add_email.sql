CREATE TABLE users
(
    id         UUID         NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    is_admin   BOOLEAN,
    is_blocked BOOLEAN,
    username   VARCHAR(255) NOT NULL,
    password   VARCHAR(255),
    email      VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);