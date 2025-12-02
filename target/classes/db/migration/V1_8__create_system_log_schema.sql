CREATE TABLE IF NOT EXISTS System_log (
    id uuid PRIMARY KEY,
    user_id int,
    event_type VARCHAR(100),
    event_description TEXT,
    ip_address VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);