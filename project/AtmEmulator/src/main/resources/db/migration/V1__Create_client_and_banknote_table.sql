CREATE TABLE client (
                        id BIGSERIAL PRIMARY KEY,
                        username VARCHAR(50) NOT NULL UNIQUE,
                        password VARCHAR(100) NOT NULL
);

CREATE TABLE account (
                         id BIGSERIAL PRIMARY KEY,
                         account_number VARCHAR(255) NOT NULL UNIQUE,
                         balance DECIMAL(19, 2) NOT NULL,
                         currency VARCHAR(10) NOT NULL,
                         client_id BIGINT NOT NULL,
                         FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE atm (
    id BIGSERIAL PRIMARY KEY
);

CREATE TABLE atm_balance (
                             atm_id BIGINT NOT NULL,
                             currency VARCHAR(10) NOT NULL,
                             balance DECIMAL(19, 2) NOT NULL,
                             PRIMARY KEY (atm_id, currency),
                             FOREIGN KEY (atm_id) REFERENCES atm(id)
);