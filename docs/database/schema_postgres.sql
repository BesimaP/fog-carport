-- Fog Carport: database
-- Lavet ud fra ERD'en "den vi bruger" (Feyzullahs version)
-- Køres i pgAdmin på databasen carport
-- Kør scriptet to gange:
--   1) med SET search_path TO public;  (den rigtige database)
--   2) med SET search_path TO test;    (test-databasen til integrationstest)

CREATE SCHEMA IF NOT EXISTS test;

SET search_path TO public;

BEGIN;

DROP TABLE IF EXISTS order_item, orders, material, status, users CASCADE;

CREATE TABLE users (
    user_id       serial        PRIMARY KEY,
    name          varchar(100)  NOT NULL,
    email         varchar(100)  NOT NULL UNIQUE,
    phone_number  varchar(20)   NOT NULL,
    address       varchar(200)  NOT NULL,
    password_hash varchar(100)  NOT NULL,
    is_admin      boolean       NOT NULL DEFAULT false
);

CREATE TABLE status (
    status_id serial       PRIMARY KEY,
    name      varchar(30)  NOT NULL UNIQUE
);

CREATE TABLE material (
    material_id serial         PRIMARY KEY,
    item_number varchar(20)    NOT NULL UNIQUE,
    name        varchar(100)   NOT NULL,
    unit        varchar(10)    NOT NULL,
    price       numeric(10,2)  NOT NULL
);

CREATE TABLE orders (
    order_id    serial         PRIMARY KEY,
    user_id     int            NOT NULL REFERENCES users (user_id),
    width       int            NOT NULL,
    length      int            NOT NULL,
    height      int            NOT NULL,
    shed_width  int,
    shed_length int,
    comment     text,
    status_id   int            NOT NULL REFERENCES status (status_id),
    cost_price  numeric(10,2),
    sales_price numeric(10,2),
    created_at  timestamp      NOT NULL DEFAULT now()
);

CREATE TABLE order_item (
    item_id     serial        PRIMARY KEY,
    order_id    int           NOT NULL REFERENCES orders (order_id) ON DELETE CASCADE,
    material_id int           NOT NULL REFERENCES material (material_id),
    length      int,
    quantity    int           NOT NULL,
    description varchar(200)
);

INSERT INTO status (name) VALUES
    ('Forespørgsel'),
    ('Tilbud sendt'),
    ('Betalt');

COMMIT;