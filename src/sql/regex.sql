CREATE TABLE regex
(
    id VARCHAR(100) PRIMARY KEY NOT NULL,
    seed VARCHAR(200),
    regex VARCHAR(200),
    start INT(10),
    thread INT(10),
    data VARCHAR(2000),
    name VARCHAR(255),
    create_time DATETIME DEFAULT 'CURRENT_TIMESTAMP',
    update_time DATETIME DEFAULT 'CURRENT_TIMESTAMP'
);