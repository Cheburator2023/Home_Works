create sequence if not exists client_SEQ start with 1 increment by 1;
create sequence if not exists address_SEQ start with 1 increment by 1;
create sequence if not exists phone_SEQ start with 1 increment by 1;

CREATE TABLE IF NOT EXISTS address (
                         id   bigint not null primary key,
                         street VARCHAR(50)
);

ALTER TABLE IF EXISTS client
                        ADD COLUMN address_id BIGINT,
                        ADD CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES address(id)
;

CREATE TABLE IF NOT EXISTS phone (
                       id   bigint not null primary key,
                       number VARCHAR(50),
                       client_id BIGINT,
                       CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client(id)
);