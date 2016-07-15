CREATE TABLE task
(
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    name VARCHAR(255),
    task_group VARCHAR(255),
    status VARCHAR(255),
    cron_expression VARCHAR(255),
    description VARCHAR(255),
    bean_class VARCHAR(255),
    method_param VARCHAR(255),
    method_name VARCHAR(255),
    create_time DATETIME DEFAULT 'CURRENT_TIMESTAMP',
    update_time DATETIME DEFAULT 'CURRENT_TIMESTAMP'
);