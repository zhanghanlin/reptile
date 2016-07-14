CREATE TABLE job
(
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    name VARCHAR(255),
    job_group VARCHAR(255),
    status VARCHAR(255),
    cronExpression VARCHAR(255),
    description VARCHAR(255),
    bean_class VARCHAR(255),
    method_param VARCHAR(255),
    method_name VARCHAR(255),
    create_time DATETIME DEFAULT 'CURRENT_TIMESTAMP',
    update_time DATETIME DEFAULT 'CURRENT_TIMESTAMP'
);