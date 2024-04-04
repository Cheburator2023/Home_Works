-- V1__Initial_schema.sql

CREATE TABLE client (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(30) NOT NULL
);

CREATE TABLE address (
                         id SERIAL PRIMARY KEY,
                         street VARCHAR(50) NOT NULL,
                         client_id BIGINT,
                         FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE phone (
                       id SERIAL PRIMARY KEY,
                       number VARCHAR(50) NOT NULL,
                       client_id BIGINT,
                       FOREIGN KEY (client_id) REFERENCES client(id)
);