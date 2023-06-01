CREATE TABLE user_tb (
    id uuid,
    login_id VARCHAR(255),
    user_name VARCHAR(255),
    user_status VARCHAR(255),
    user_role VARCHAR(255),
    email VARCHAR(255) ,
    user_password VARCHAR(255),
    phone VARCHAR(255),
    created_at timestamp,
    updated_at timestamp,
    PRIMARY KEY (id)
);
