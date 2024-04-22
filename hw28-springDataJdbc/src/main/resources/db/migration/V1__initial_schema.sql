-- V1__Initial_schema.sql

CREATE TABLE client (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(30) NOT NULL
);

CREATE TABLE address (
                         id BIGSERIAL PRIMARY KEY,
                         street VARCHAR(50) NOT NULL,
                         client_id BIGINT,
                         FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE phone (
                       id BIGSERIAL PRIMARY KEY,
                       number VARCHAR(50) NOT NULL,
                       client_id BIGINT,
                       FOREIGN KEY (client_id) REFERENCES client(id)
);