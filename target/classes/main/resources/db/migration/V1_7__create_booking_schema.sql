CREATE TABLE IF NOT EXISTS Booking (
    id SERIAL PRIMARY KEY,
    seats_booked int,
    created_at date DEFAULT CURRENT_DATE,
    user_id INT REFERENCES Users(id) ON DELETE CASCADE,
    tour_id INT REFERENCES Tour(id) ON DELETE CASCADE
);