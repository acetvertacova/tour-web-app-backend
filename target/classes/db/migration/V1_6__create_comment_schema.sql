CREATE TABLE IF NOT EXISTS Comment (
    id SERIAL PRIMARY KEY,
    content text NOT NULL,
    user_id int REFERENCES Users(id),
    tour_id int REFERENCES Tour(id),
    created_at date DEFAULT CURRENT_DATE
);

