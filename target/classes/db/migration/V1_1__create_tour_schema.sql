CREATE TABLE IF NOT EXISTS Tour (
    id serial PRIMARY KEY,
    name varchar(100) not null,
    country varchar(50),
    city varchar(50),
    check_in_date date,
    check_out_date date,
    max_capacity int default 20,
    available_spots int,
    price decimal,
    hotel varchar(50),
    rating int,
    description text,
    images_url text[]
);

