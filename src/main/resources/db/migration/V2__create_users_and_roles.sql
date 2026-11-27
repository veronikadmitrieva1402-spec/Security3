CREATE TABLE user_account (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    must_change_password BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE user_roles (
    user_id UUID NOT NULL REFERENCES user_account(id) ON DELETE CASCADE,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role)
);

INSERT INTO user_account (id, username, password, full_name)
VALUES (gen_random_uuid(), 'admin', '$2a$10$wT4K1q4v1g7d7Zt4Z5m5XO1r2e3w4q5r6t7y8u9i0o1p2a3s4d5f6', 'Администратор');

INSERT INTO user_roles (user_id, role) SELECT id, 'ROLE_ADMIN' FROM user_account WHERE username = 'admin';