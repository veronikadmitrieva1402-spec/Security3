CREATE TABLE request_notes (
    id UUID PRIMARY KEY,
    request_id UUID NOT NULL REFERENCES security_requests(id) ON DELETE CASCADE,
    author_name VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    pinned BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE tags (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE request_tags (
    request_id UUID NOT NULL REFERENCES security_requests(id) ON DELETE CASCADE,
    tag_id UUID NOT NULL REFERENCES tags(id) ON DELETE CASCADE,
    PRIMARY KEY (request_id, tag_id)
);

CREATE TABLE audit_events (
    id UUID PRIMARY KEY,
    username VARCHAR(255),
    event_type VARCHAR(100) NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    entity_id VARCHAR(100),
    description TEXT,
    correlation_id VARCHAR(100),
    created_at TIMESTAMP NOT NULL
);