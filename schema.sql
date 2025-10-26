-- Drop existing tables
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS trains;
DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL
);

-- Create trains table
CREATE TABLE trains (
    train_id INT PRIMARY KEY AUTO_INCREMENT,
    train_number VARCHAR(10) UNIQUE NOT NULL,
    train_name VARCHAR(100) NOT NULL,
    source VARCHAR(50) NOT NULL,
    destination VARCHAR(50) NOT NULL,
    total_seats INT NOT NULL,
    fare DECIMAL(10,2) NOT NULL,
    departure_time VARCHAR(10)
);

-- Create bookings table
CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    train_id INT NOT NULL,
    passenger_name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    seat_number INT NOT NULL,
    fare DECIMAL(10,2) NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'Confirmed',
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (train_id) REFERENCES trains(train_id)
);

-- Insert trains with multiple options per popular route
INSERT INTO trains (train_number, train_name, source, destination, total_seats, fare, departure_time) VALUES
-- Delhi to Mumbai (4 trains)
('12301', 'Rajdhani Express', 'Delhi', 'Mumbai', 30, 1500.00, '06:00'),
('12951', 'Mumbai Rajdhani', 'Delhi', 'Mumbai', 30, 1550.00, '16:30'),
('12953', 'August Kranti', 'Delhi', 'Mumbai', 30, 1450.00, '11:25'),
('12955', 'Jaipur Superfast', 'Delhi', 'Mumbai', 30, 1400.00, '19:50'),
-- Mumbai to Delhi (4 trains)
('12302', 'Mumbai Rajdhani', 'Mumbai', 'Delhi', 30, 1700.00, '17:00'),
('12952', 'Delhi Rajdhani', 'Mumbai', 'Delhi', 30, 1650.00, '20:15'),
('12954', 'Golden Temple', 'Mumbai', 'Delhi', 30, 1600.00, '08:45'),
('12956', 'Jaipur Express', 'Mumbai', 'Delhi', 30, 1550.00, '12:30'),
-- Delhi to Bangalore (3 trains)
('12303', 'Shatabdi Express', 'Delhi', 'Bangalore', 30, 2000.00, '08:30'),
('12429', 'Bangalore Rajdhani', 'Delhi', 'Bangalore', 30, 2100.00, '20:05'),
('12257', 'Bangalore Duronto', 'Delhi', 'Bangalore', 30, 1950.00, '15:50'),
-- Bangalore to Delhi (3 trains)
('12304', 'Karnataka Express', 'Bangalore', 'Delhi', 30, 1900.00, '20:00'),
('12430', 'Delhi Rajdhani', 'Bangalore', 'Delhi', 30, 2050.00, '21:30'),
('12258', 'Delhi Duronto', 'Bangalore', 'Delhi', 30, 1850.00, '06:15'),
-- Mumbai to Kolkata (3 trains)
('12305', 'Duronto Express', 'Mumbai', 'Kolkata', 30, 1800.00, '10:00'),
('12809', 'Howrah Mail', 'Mumbai', 'Kolkata', 30, 1750.00, '19:55'),
('12261', 'Kolkata Duronto', 'Mumbai', 'Kolkata', 30, 1850.00, '14:10'),
-- Kolkata to Mumbai (3 trains)
('12306', 'Howrah Mail', 'Kolkata', 'Mumbai', 30, 1750.00, '22:30'),
('12810', 'Mumbai Mail', 'Kolkata', 'Mumbai', 30, 1800.00, '18:40'),
('12262', 'Mumbai Duronto', 'Kolkata', 'Mumbai', 30, 1900.00, '08:35'),
-- Delhi to Kolkata (3 trains)
('12307', 'Garib Rath', 'Delhi', 'Kolkata', 30, 1200.00, '14:00'),
('12313', 'Rajdhani Express', 'Delhi', 'Kolkata', 30, 1400.00, '17:00'),
('12273', 'Howrah Duronto', 'Delhi', 'Kolkata', 30, 1350.00, '21:25'),
-- Kolkata to Delhi (3 trains)
('12308', 'Kolkata Express', 'Kolkata', 'Delhi', 30, 1350.00, '11:20'),
('12314', 'Delhi Rajdhani', 'Kolkata', 'Delhi', 30, 1450.00, '16:55'),
('12274', 'Delhi Duronto', 'Kolkata', 'Delhi', 30, 1300.00, '08:50'),
-- Chennai to Delhi (3 trains)
('12311', 'Tamil Nadu Express', 'Chennai', 'Delhi', 30, 2100.00, '21:45'),
('12615', 'Grand Trunk Express', 'Chennai', 'Delhi', 30, 2000.00, '19:15'),
('12621', 'Tamil Nadu Mail', 'Chennai', 'Delhi', 30, 1950.00, '06:30'),
-- Delhi to Chennai (3 trains)
('12312', 'Delhi Express', 'Delhi', 'Chennai', 30, 2050.00, '09:00'),
('12616', 'Chennai Mail', 'Delhi', 'Chennai', 30, 1950.00, '15:30'),
('12622', 'Tamil Nadu Express', 'Delhi', 'Chennai', 30, 2000.00, '22:45'),
-- Chennai to Bangalore (3 trains)
('12315', 'Bangalore Mail', 'Chennai', 'Bangalore', 30, 900.00, '23:00'),
('12639', 'Brindavan Express', 'Chennai', 'Bangalore', 30, 850.00, '06:00'),
('12641', 'Tirupati Express', 'Chennai', 'Bangalore', 30, 880.00, '14:30'),
-- Bangalore to Chennai (3 trains)
('12316', 'Chennai Mail', 'Bangalore', 'Chennai', 30, 950.00, '07:30'),
('12640', 'Chennai Express', 'Bangalore', 'Chennai', 30, 900.00, '15:50'),
('12642', 'Kaveri Express', 'Bangalore', 'Chennai', 30, 920.00, '20:15'),
-- Other routes
('12309', 'Chennai Express', 'Mumbai', 'Chennai', 30, 1600.00, '16:30'),
('12310', 'Bangalore Mail', 'Chennai', 'Mumbai', 30, 1550.00, '23:00'),
('12317', 'Kolkata Mail', 'Delhi', 'Pune', 30, 1400.00, '15:00'),
('12318', 'Mumbai Express', 'Bangalore', 'Mumbai', 30, 1650.00, '12:00'),
('12665', 'Bangalore Superfast', 'Bangalore', 'Mumbai', 30, 1700.00, '18:25'),
('12319', 'Howrah Express', 'Bangalore', 'Kolkata', 30, 1850.00, '18:45'),
('12320', 'Bangalore Express', 'Kolkata', 'Bangalore', 30, 1900.00, '19:30'),
('12321', 'Chennai Rajdhani', 'Chennai', 'Kolkata', 30, 1750.00, '20:15'),
('12322', 'Coromandel Express', 'Kolkata', 'Chennai', 30, 1800.00, '14:50'),
('12323', 'Pune Express', 'Pune', 'Delhi', 30, 1450.00, '16:00'),
('12324', 'Chennai Express', 'Chennai', 'Pune', 30, 1300.00, '10:30'),
('12325', 'Pune Mail', 'Pune', 'Bangalore', 30, 1100.00, '21:00'),
('12326', 'Bangalore Pune', 'Bangalore', 'Pune', 30, 1050.00, '08:15'),
('12701', 'Deccan Queen', 'Mumbai', 'Pune', 30, 800.00, '07:15'),
('12702', 'Pune Express', 'Pune', 'Mumbai', 30, 850.00, '18:00'),
('12703', 'Shivaji Express', 'Pune', 'Mumbai', 30, 820.00, '05:50');
