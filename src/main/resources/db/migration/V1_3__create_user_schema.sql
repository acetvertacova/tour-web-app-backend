CREATE TABLE IF NOT EXISTS Users (
    id serial PRIMARY KEY,
    username varchar(50) NOT NULL UNIQUE,
    email varchar(50) NOT NULL UNIQUE,
    password varchar(200) NOT NULL, --chkpass check if can use
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO Users (username, email, password)
VALUES ('anastasia', 'anastasia@example.com', '$2a$10$7H9EvnsnCRKdLfXPkgpfH.isaSYARz7OdFzhX/8gP7LR1Nyi1gCI.'); -- password: admin1
