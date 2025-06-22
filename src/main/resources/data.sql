INSERT INTO users (name, email, phone, city, created_at) VALUES ('Diego', 'diego.alfa.software.555@gmail.com', '123456', 'Bogotá', CURRENT_TIMESTAMP);
-- Repite para users 2, 3, 4, 5

INSERT INTO hotel ( num_apartments, num_rooms) VALUES (10, 20);
-- Repite para hotel 2, 3

INSERT INTO rooms (capacity, description, price, url_img, hotel_id) VALUES (2, 'Habitación doble', 50000, 'img1.jpg', 1);
-- Repite para rooms 2, 3, 4, 5


INSERT INTO reservations (
    user_id, hotel_id, room_id, num_persons, total, status, check_in, check_out
) VALUES 
(1, 1, 1, 3, 100000, 'confirmada', '2025-05-10', '2025-05-12'),
(1, 1, 1, 2, 50000, 'confirmada', '2025-06-01', '2025-06-02'),
(1, 1, 1, 3, 150000, 'confirmada', '2025-07-15', '2025-07-17'),
(1, 1, 1, 4, 200000, 'confirmada', '2025-08-20', '2025-08-23'),
(1, 1, 1, 2, 100000, 'confirmada', '2025-09-05', '2025-09-07');