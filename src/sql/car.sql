CREATE TABLE car
(
    id VARCHAR(100) PRIMARY KEY NOT NULL,
    car_name VARCHAR(255),
    price VARCHAR(255),
    on_time VARCHAR(255),
    mileage VARCHAR(255),
    speed_case VARCHAR(255),
    inspect_expire VARCHAR(255),
    safe_expire VARCHAR(255),
    accident VARCHAR(255),
    user_name VARCHAR(255),
    phone VARCHAR(255),
    create_time DATETIME DEFAULT ''CURRENT_TIMESTAMP'' NOT NULL,
    url VARCHAR(255),
    update_time DATETIME DEFAULT ''CURRENT_TIMESTAMP'',
    address VARCHAR(255),
    source VARCHAR(255)
);