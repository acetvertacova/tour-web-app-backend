CREATE TABLE IF NOT EXISTS User_Role (
    user_id INT REFERENCES Users(id) ON DELETE CASCADE,
    role_id INT REFERENCES Role(id) ON DELETE CASCADE,
    CONSTRAINT user_role_pk PRIMARY KEY(user_id, role_id)
);

