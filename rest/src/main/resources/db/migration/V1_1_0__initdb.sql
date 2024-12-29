CREATE TABLE authors
(
    id         UUID         NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_authors PRIMARY KEY (id)
);

CREATE TABLE book_authors
(
    author_id UUID NOT NULL,
    book_id   UUID NOT NULL
);

CREATE TABLE book_rentals
(
    id              UUID NOT NULL,
    book_id         UUID NOT NULL,
    user_id         UUID NOT NULL,
    subscription_id UUID NOT NULL,
    start_date      date NOT NULL,
    end_date        date,
    CONSTRAINT pk_book_rentals PRIMARY KEY (id)
);

CREATE TABLE books
(
    id               UUID         NOT NULL,
    title            VARCHAR(255) NOT NULL,
    pages            INTEGER      NOT NULL,
    price            DECIMAL      NOT NULL,
    price_per_rental DECIMAL      NOT NULL,
    stock            INTEGER      NOT NULL,
    created_on       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_on       TIMESTAMP WITHOUT TIME ZONE,
    is_deleted       BOOLEAN,
    CONSTRAINT pk_books PRIMARY KEY (id)
);

CREATE TABLE purchases
(
    id            UUID    NOT NULL,
    user_id       UUID    NOT NULL,
    book_id       UUID    NOT NULL,
    price         DECIMAL NOT NULL,
    purchase_date date    NOT NULL,
    CONSTRAINT pk_purchases PRIMARY KEY (id)
);

CREATE TABLE subscriptions
(
    id         UUID NOT NULL,
    user_id    UUID NOT NULL,
    start_date date NOT NULL,
    end_date   date NOT NULL,
    can_rent   BOOLEAN,
    CONSTRAINT pk_subscriptions PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         UUID         NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    is_admin   BOOLEAN,
    is_blocked BOOLEAN,
    username   VARCHAR(255) NOT NULL,
    password   VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE book_rentals
    ADD CONSTRAINT FK_BOOK_RENTALS_ON_BOOK FOREIGN KEY (book_id) REFERENCES books (id);

ALTER TABLE book_rentals
    ADD CONSTRAINT FK_BOOK_RENTALS_ON_SUBSCRIPTION FOREIGN KEY (subscription_id) REFERENCES subscriptions (id);

ALTER TABLE book_rentals
    ADD CONSTRAINT FK_BOOK_RENTALS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE purchases
    ADD CONSTRAINT FK_PURCHASES_ON_BOOK FOREIGN KEY (book_id) REFERENCES books (id);

ALTER TABLE purchases
    ADD CONSTRAINT FK_PURCHASES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE subscriptions
    ADD CONSTRAINT FK_SUBSCRIPTIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE book_authors
    ADD CONSTRAINT fk_booaut_on_author FOREIGN KEY (author_id) REFERENCES authors (id);

ALTER TABLE book_authors
    ADD CONSTRAINT fk_booaut_on_book FOREIGN KEY (book_id) REFERENCES books (id);