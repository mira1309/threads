CREATE TABLE car (
id SERIAL PRIMARY KEY,
mark TEXT,
model TEXT,
price BIGSERIAL
);

CREATE TABLE person (
id SERIAL PRIMARY KEY,
name TEXT,
age INTEGER,
driver license BOOLEAN,
car_id SERIAL REFERENCES CAR (id)
);

