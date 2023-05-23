CREATE TABLE user_tb (
    id uuid,
    login_id VARCHAR(255),
    name VARCHAR(255),
    state VARCHAR(255),
    role VARCHAR(255),
    email VARCHAR(255) ,
    password VARCHAR(255),
    phone VARCHAR(255),
    created_at timestamp,
    updated_at timestamp,
    PRIMARY KEY (id)
);
