INSERT INTO PRODUCT (id, name, automatic_acceptation, automatic_exit, minimum_client_status) VALUES (1, 'Assurance', 'false', 'false', 'BRONZE');

INSERT INTO PRODUCT (id, name, automatic_acceptation, automatic_exit, minimum_client_status) VALUES (2, 'Carte de credit', 'false', 'false', 'SILVER');

INSERT INTO PRODUCT (id, name, automatic_acceptation, automatic_exit, minimum_client_status) VALUES (3, 'Carte de debit', 'true', 'true', 'BRONZE');

INSERT INTO PRODUCT (id, name, automatic_acceptation, automatic_exit, minimum_client_status) VALUES (4, 'Assurance Premium', 'false', 'false', 'GOLD');

INSERT INTO CLIENT (id, username, password, name, status) VALUES (1, 'olivier', 'pass', 'odg18', 'SILVER');

INSERT INTO CLIENT (id, username, password, name, status) VALUES (2, 'user2', 'pass', 'user2', 'SILVER');

INSERT INTO CLIENT (id, username, password, name, status) VALUES (3, 'user3', 'pass', 'user3', 'SILVER');

INSERT INTO CLIENT_PRODUCT_ASSOCIATION (id, client_id, product_id, timestamp, status) VALUES (1, 1, 2, CURRENT_TIMESTAMP, 'SUBSCRIBED');

INSERT INTO CLIENT_PRODUCT_ASSOCIATION (id, client_id, product_id, timestamp, status) VALUES (2, 2, 1, CURRENT_TIMESTAMP, 'WAITING_FOR_SUBSCRIBE');

INSERT INTO CLIENT_PRODUCT_ASSOCIATION (id, client_id, product_id, timestamp, status) VALUES (3, 3, 1, CURRENT_TIMESTAMP, 'WAITING_FOR_UNSUBSCRIBE');

COMMIT;