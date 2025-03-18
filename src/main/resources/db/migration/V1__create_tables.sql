CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE pokemon (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    catch_rate DECIMAL(5,2) NOT NULL,
    characteristic VARCHAR(50) NOT NULL
);

CREATE TABLE pokemon_types (
    pokemon_id INT REFERENCES pokemon(id),
    type VARCHAR(50) NOT NULL,
    PRIMARY KEY (pokemon_id, type)
);

CREATE TABLE user_pokemon (
    user_id INT REFERENCES users(id),
    pokemon_id INT REFERENCES pokemon(id),
    PRIMARY KEY (user_id, pokemon_id)
);

CREATE TABLE friends (
    user_id INT REFERENCES users(id),
    friend_id INT REFERENCES users(id),
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE trade_requests (
    id SERIAL PRIMARY KEY,
    requester_id INT REFERENCES users(id),
    target_id INT REFERENCES users(id),
    requester_pokemon_id INT REFERENCES pokemon(id),
    target_pokemon_id INT REFERENCES pokemon(id),
    status VARCHAR(20) DEFAULT 'PENDING' -- PENDING, ACCEPTED, REJECTED, CANCELLED
);