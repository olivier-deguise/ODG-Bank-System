

CREATE TABLE IF NOT EXISTS CLIENT (id integer, username varchar, password varchar, name varchar, status varchar);

CREATE TABLE IF NOT EXISTS PRODUCT (id integer, name varchar, automatic_acceptation boolean, automatic_exit boolean, minimum_client_status varchar);

CREATE TABLE IF NOT EXISTS CLIENT_PRODUCT_ASSOCIATION (id integer, client_id integer, product_id integer, timestamp timestamp, status varchar);
