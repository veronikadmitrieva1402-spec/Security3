CREATE TABLE service_types (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE security_requests (
    id UUID PRIMARY KEY,
    request_number VARCHAR(50) UNIQUE,
    object_address VARCHAR(255) NOT NULL,
    service_type_id UUID REFERENCES service_types(id),
    status VARCHAR(50) NOT NULL,
    risk_level INT NOT NULL DEFAULT 1,
    urgent BOOLEAN NOT NULL DEFAULT FALSE,
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);