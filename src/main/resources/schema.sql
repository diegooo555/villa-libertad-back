-- Tabla: users
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(20),
    city VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla: hotel
CREATE TABLE hotel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    num_apartments INT NOT NULL,
    num_rooms INT NOT NULL
);

-- Tabla: rooms
CREATE TABLE rooms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    capacity INT NOT NULL,
    description VARCHAR(255),
    price DECIMAL(10,2),
    url_img VARCHAR(255),
    hotel_id INT,
    FOREIGN KEY (hotel_id) REFERENCES hotel(id)
);

-- Tabla: reservations
CREATE TABLE reservations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    hotel_id INT NOT NULL,
    room_id INT NOT NULL,
    num_persons INT NOT NULL,
    total DECIMAL(10,2),
    status VARCHAR(50),
    check_in DATE,
    check_out DATE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (hotel_id) REFERENCES hotel(id),
    FOREIGN KEY (room_id) REFERENCES rooms(id)
);
