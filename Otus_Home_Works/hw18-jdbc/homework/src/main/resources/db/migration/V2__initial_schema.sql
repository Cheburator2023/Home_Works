create table test
(
    id   int,
    name varchar(50)
);
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);
CREATE TABLE manager (
                         no BIGINT PRIMARY KEY,
                         label VARCHAR(255),
                         param1 VARCHAR(255)
);
