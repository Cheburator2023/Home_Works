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

CREATE TABLE cash_keeper (
    id BIGSERIAL PRIMARY KEY
);

CREATE TABLE cash_keeper_balance (
                                     cash_keeper_id BIGINT NOT NULL,
                                     currency VARCHAR(255) NOT NULL,
                                     count INT NOT NULL,
                                     PRIMARY KEY (cash_keeper_id, currency),
                                     FOREIGN KEY (cash_keeper_id) REFERENCES cash_keeper(id)
);

CREATE TABLE atm_banknotes (
                               atm_id BIGINT NOT NULL,
                               banknote VARCHAR(255) NOT NULL,
                               count INT NOT NULL,
                               PRIMARY KEY (atm_id, banknote),
                               FOREIGN KEY (atm_id) REFERENCES atm(id)
);
CREATE TABLE account_balance_history (
                                         id BIGSERIAL PRIMARY KEY,
                                         account_id BIGINT NOT NULL,
                                         change_amount DECIMAL(19, 2) NOT NULL,
                                         timestamp TIMESTAMP NOT NULL,
                                         FOREIGN KEY (account_id) REFERENCES account(id)
);
ALTER TABLE atm
    ADD COLUMN cash_keeper_id BIGINT,
ADD FOREIGN KEY (cash_keeper_id) REFERENCES cash_keeper(id);

INSERT INTO client (username, password) VALUES ('user', '$2a$10$u.s4d1VGHBOwRGQiUfdG.un1F3b3s9vUsbEAQ0D7pqdA.FWO9H4Wi');

INSERT INTO account (account_number, balance, currency, client_id) VALUES
                                                                       ('RUB123', 1000.00, 'RUB', 1),
                                                                       ('USD123', 100.00, 'DOLLAR', 1),
                                                                       ('EUR123', 100.00, 'EURO', 1);

INSERT INTO cash_keeper (id) VALUES (1);

INSERT INTO cash_keeper_balance (cash_keeper_id, currency, count) VALUES
                                                                      (1, 'RUB', 1000),
                                                                      (1, 'DOLLAR', 500),
                                                                      (1, 'EURO', 300);

INSERT INTO atm (id, cash_keeper_id) VALUES (1, 1);

INSERT INTO atm_banknotes (atm_id, banknote, count) VALUES
                                                        (1, 'RUBLES100', 50),
                                                        (1, 'RUBLES500', 30),
                                                        (1, 'RUBLES1000', 20),
                                                        (1, 'RUBLES5000', 10),
                                                        (1, 'DOLLARS50', 25),
                                                        (1, 'DOLLARS100', 15),
                                                        (1, 'EUROS50', 20),
                                                        (1, 'EUROS100', 10);