-- Create users table (only if not exists)
CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL
);

-- Create trains table (only if not exists)
CREATE TABLE IF NOT EXISTS trains (
    train_id INT PRIMARY KEY AUTO_INCREMENT,
    train_number VARCHAR(10) UNIQUE NOT NULL,
    train_name VARCHAR(100) NOT NULL,
    source VARCHAR(50) NOT NULL,
    destination VARCHAR(50) NOT NULL,
    total_seats INT NOT NULL,
    fare DECIMAL(10,2) NOT NULL,
    departure_time VARCHAR(10),
    arrival_time VARCHAR(10),
    journey_duration VARCHAR(10)
);

-- Create bookings table (only if not exists)
CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    train_id INT NOT NULL,
    passenger_name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    seat_number INT NOT NULL,
    fare DECIMAL(10,2) NOT NULL,
    travel_date DATE NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'Confirmed',
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (train_id) REFERENCES trains(train_id)
);

-- Insert sample trains with timing details
INSERT IGNORE INTO trains (train_number, train_name, source, destination, total_seats, fare, departure_time, arrival_time, journey_duration) VALUES

-- ========== DELHI TO MUMBAI (6 trains) ==========
('12301', 'Rajdhani Express', 'Delhi', 'Mumbai', 30, 1500.00, '05:30', '22:15', '16h 45m'),
('12951', 'Mumbai Rajdhani', 'Delhi', 'Mumbai', 30, 1550.00, '08:20', '01:30', '17h 10m'),
('12953', 'August Kranti', 'Delhi', 'Mumbai', 30, 1450.00, '11:25', '04:55', '17h 30m'),
('12955', 'Jaipur Superfast', 'Delhi', 'Mumbai', 30, 1400.00, '14:30', '07:45', '17h 15m'),
('12957', 'Golden Chariot', 'Delhi', 'Mumbai', 30, 1600.00, '18:00', '11:20', '17h 20m'),
('12959', 'Night Express', 'Delhi', 'Mumbai', 30, 1480.00, '22:30', '16:10', '17h 40m'),

-- ========== MUMBAI TO DELHI (6 trains) ==========
('12302', 'Mumbai Rajdhani', 'Mumbai', 'Delhi', 30, 1700.00, '05:15', '22:35', '17h 20m'),
('12952', 'Delhi Rajdhani', 'Mumbai', 'Delhi', 30, 1650.00, '09:30', '03:00', '17h 30m'),
('12954', 'Golden Temple', 'Mumbai', 'Delhi', 30, 1600.00, '12:45', '06:15', '17h 30m'),
('12956', 'Jaipur Express', 'Mumbai', 'Delhi', 30, 1550.00, '16:10', '09:40', '17h 30m'),
('12958', 'Delhi Superfast', 'Mumbai', 'Delhi', 30, 1720.00, '19:25', '13:05', '17h 40m'),
('12960', 'Overnight Express', 'Mumbai', 'Delhi', 30, 1580.00, '23:00', '17:00', '18h 00m'),

-- ========== DELHI TO BANGALORE (5 trains) ==========
('12429', 'Bangalore Rajdhani', 'Delhi', 'Bangalore', 30, 2100.00, '06:00', '18:45', '36h 45m'),
('12257', 'Bangalore Duronto', 'Delhi', 'Bangalore', 30, 1950.00, '10:30', '23:30', '37h 00m'),
('12303', 'Karnataka Express', 'Delhi', 'Bangalore', 30, 2000.00, '14:15', '03:15', '37h 00m'),
('12431', 'Bangalore Superfast', 'Delhi', 'Bangalore', 30, 2050.00, '18:45', '08:00', '37h 15m'),
('12433', 'Bangalore Mail', 'Delhi', 'Bangalore', 30, 1900.00, '22:10', '11:45', '37h 35m'),

-- ========== BANGALORE TO DELHI (5 trains) ==========
('12430', 'Delhi Rajdhani', 'Bangalore', 'Delhi', 30, 2050.00, '05:30', '18:30', '37h 00m'),
('12258', 'Delhi Duronto', 'Bangalore', 'Delhi', 30, 1850.00, '09:45', '23:00', '37h 15m'),
('12304', 'Delhi Express', 'Bangalore', 'Delhi', 30, 1900.00, '13:20', '02:45', '37h 25m'),
('12432', 'Delhi Superfast', 'Bangalore', 'Delhi', 30, 2000.00, '17:00', '06:30', '37h 30m'),
('12434', 'Capitol Express', 'Bangalore', 'Delhi', 30, 1920.00, '21:30', '11:15', '37h 45m'),

-- ========== MUMBAI TO KOLKATA (5 trains) ==========
('12809', 'Howrah Mail', 'Mumbai', 'Kolkata', 30, 1750.00, '06:15', '15:00', '32h 45m'),
('12261', 'Kolkata Duronto', 'Mumbai', 'Kolkata', 30, 1850.00, '10:30', '19:45', '33h 15m'),
('12305', 'Howrah Express', 'Mumbai', 'Kolkata', 30, 1800.00, '14:45', '00:15', '33h 30m'),
('12811', 'Kolkata Superfast', 'Mumbai', 'Kolkata', 30, 1820.00, '18:20', '04:00', '33h 40m'),
('12813', 'Howrah Night Mail', 'Mumbai', 'Kolkata', 30, 1780.00, '22:00', '08:00', '34h 00m'),

-- ========== KOLKATA TO MUMBAI (5 trains) ==========
('12810', 'Mumbai Mail', 'Kolkata', 'Mumbai', 30, 1800.00, '05:45', '14:45', '33h 00m'),
('12262', 'Mumbai Duronto', 'Kolkata', 'Mumbai', 30, 1900.00, '09:30', '19:00', '33h 30m'),
('12306', 'Mumbai Express', 'Kolkata', 'Mumbai', 30, 1750.00, '13:15', '23:00', '33h 45m'),
('12812', 'Mumbai Superfast', 'Kolkata', 'Mumbai', 30, 1850.00, '17:40', '03:45', '34h 05m'),
('12814', 'CST Mail', 'Kolkata', 'Mumbai', 30, 1780.00, '21:30', '07:45', '34h 15m'),

-- ========== DELHI TO KOLKATA (6 trains) ==========
('12313', 'Rajdhani Express', 'Delhi', 'Kolkata', 30, 1400.00, '05:20', '06:00', '24h 40m'),
('12273', 'Howrah Duronto', 'Delhi', 'Kolkata', 30, 1350.00, '08:45', '09:30', '24h 45m'),
('12307', 'Howrah Superfast', 'Delhi', 'Kolkata', 30, 1200.00, '11:30', '12:30', '25h 00m'),
('12315', 'Kolkata Express', 'Delhi', 'Kolkata', 30, 1380.00, '14:50', '16:00', '25h 10m'),
('12317', 'Howrah Mail', 'Delhi', 'Kolkata', 30, 1300.00, '18:15', '19:45', '25h 30m'),
('12319', 'Sealdah Express', 'Delhi', 'Kolkata', 30, 1250.00, '21:45', '23:30', '25h 45m'),

-- ========== KOLKATA TO DELHI (6 trains) ==========
('12314', 'Delhi Rajdhani', 'Kolkata', 'Delhi', 30, 1450.00, '05:00', '05:45', '24h 45m'),
('12274', 'Delhi Duronto', 'Kolkata', 'Delhi', 30, 1300.00, '08:30', '09:30', '25h 00m'),
('12308', 'Delhi Superfast', 'Kolkata', 'Delhi', 30, 1350.00, '11:20', '12:45', '25h 25m'),
('12316', 'New Delhi Express', 'Kolkata', 'Delhi', 30, 1400.00, '14:40', '16:15', '25h 35m'),
('12318', 'Capitol Express', 'Kolkata', 'Delhi', 30, 1280.00, '17:55', '20:00', '26h 05m'),
('12320', 'Delhi Mail', 'Kolkata', 'Delhi', 30, 1320.00, '21:15', '23:45', '26h 30m'),

-- ========== CHENNAI TO DELHI (5 trains) ==========
('12621', 'Tamil Nadu Express', 'Chennai', 'Delhi', 30, 1950.00, '06:30', '19:15', '36h 45m'),
('12615', 'Grand Trunk Express', 'Chennai', 'Delhi', 30, 2000.00, '10:15', '23:30', '37h 15m'),
('12311', 'Tamil Nadu Mail', 'Chennai', 'Delhi', 30, 2100.00, '14:45', '04:15', '37h 30m'),
('12623', 'Delhi Superfast', 'Chennai', 'Delhi', 30, 2050.00, '18:20', '08:15', '37h 55m'),
('12625', 'Capitol Mail', 'Chennai', 'Delhi', 30, 1980.00, '22:00', '12:30', '38h 30m'),

-- ========== DELHI TO CHENNAI (5 trains) ==========
('12622', 'Tamil Nadu Express', 'Delhi', 'Chennai', 30, 2000.00, '05:45', '18:45', '37h 00m'),
('12616', 'Chennai Mail', 'Delhi', 'Chennai', 30, 1950.00, '09:30', '23:00', '37h 30m'),
('12312', 'Chennai Express', 'Delhi', 'Chennai', 30, 2050.00, '13:15', '03:00', '37h 45m'),
('12624', 'Chennai Superfast', 'Delhi', 'Chennai', 30, 2080.00, '17:00', '07:15', '38h 15m'),
('12626', 'Madras Mail', 'Delhi', 'Chennai', 30, 1920.00, '21:30', '12:00', '38h 30m'),

-- ========== CHENNAI TO BANGALORE (6 trains) ==========
('12639', 'Brindavan Express', 'Chennai', 'Bangalore', 30, 850.00, '06:00', '12:30', '6h 30m'),
('12641', 'Tirupati Express', 'Chennai', 'Bangalore', 30, 880.00, '09:15', '16:00', '6h 45m'),
('12315', 'Bangalore Mail', 'Chennai', 'Bangalore', 30, 900.00, '12:30', '19:15', '6h 45m'),
('12643', 'Shatabdi Express', 'Chennai', 'Bangalore', 30, 920.00, '15:45', '22:45', '7h 00m'),
('12645', 'Bangalore Express', 'Chennai', 'Bangalore', 30, 870.00, '18:30', '01:45', '7h 15m'),
('12647', 'Night Express', 'Chennai', 'Bangalore', 30, 860.00, '22:00', '05:30', '7h 30m'),

-- ========== BANGALORE TO CHENNAI (6 trains) ==========
('12640', 'Chennai Express', 'Bangalore', 'Chennai', 30, 900.00, '05:30', '12:15', '6h 45m'),
('12642', 'Kaveri Express', 'Bangalore', 'Chennai', 30, 920.00, '08:45', '15:45', '7h 00m'),
('12316', 'Chennai Mail', 'Bangalore', 'Chennai', 30, 950.00, '11:30', '18:30', '7h 00m'),
('12644', 'Chennai Superfast', 'Bangalore', 'Chennai', 30, 930.00, '14:20', '21:30', '7h 10m'),
('12646', 'Madras Express', 'Bangalore', 'Chennai', 30, 890.00, '17:45', '01:00', '7h 15m'),
('12648', 'Overnight Mail', 'Bangalore', 'Chennai', 30, 880.00, '21:15', '05:00', '7h 45m'),

-- ========== MUMBAI TO CHENNAI (5 trains) ==========
('12163', 'Chennai Express', 'Mumbai', 'Chennai', 30, 1600.00, '06:30', '01:15', '18h 45m'),
('12309', 'Chennai Mail', 'Mumbai', 'Chennai', 30, 1550.00, '10:45', '05:45', '19h 00m'),
('12165', 'Chennai Superfast', 'Mumbai', 'Chennai', 30, 1650.00, '14:20', '09:45', '19h 25m'),
('12167', 'Madras Express', 'Mumbai', 'Chennai', 30, 1580.00, '18:00', '13:30', '19h 30m'),
('12169', 'Overnight Mail', 'Mumbai', 'Chennai', 30, 1520.00, '22:15', '18:15', '20h 00m'),

-- ========== CHENNAI TO MUMBAI (5 trains) ==========
('12164', 'Mumbai Express', 'Chennai', 'Mumbai', 30, 1550.00, '06:15', '01:00', '18h 45m'),
('12310', 'Mumbai Mail', 'Chennai', 'Mumbai', 30, 1600.00, '10:00', '05:15', '19h 15m'),
('12166', 'CST Superfast', 'Chennai', 'Mumbai', 30, 1620.00, '13:45', '09:30', '19h 45m'),
('12168', 'Mumbai Duronto', 'Chennai', 'Mumbai', 30, 1650.00, '17:30', '13:45', '20h 15m'),
('12170', 'Night Express', 'Chennai', 'Mumbai', 30, 1540.00, '21:45', '18:30', '20h 45m'),

-- ========== BANGALORE TO MUMBAI (5 trains) ==========
('12318', 'Mumbai Express', 'Bangalore', 'Mumbai', 30, 1650.00, '06:30', '00:45', '18h 15m'),
('12665', 'Bangalore Superfast', 'Bangalore', 'Mumbai', 30, 1700.00, '10:15', '04:45', '18h 30m'),
('12501', 'Mumbai Mail', 'Bangalore', 'Mumbai', 30, 1620.00, '13:45', '08:30', '18h 45m'),
('12503', 'CST Express', 'Bangalore', 'Mumbai', 30, 1680.00, '17:20', '12:15', '18h 55m'),
('12505', 'Night Rider', 'Bangalore', 'Mumbai', 30, 1600.00, '21:00', '16:30', '19h 30m'),

-- ========== MUMBAI TO BANGALORE (5 trains) ==========
('12502', 'Bangalore Express', 'Mumbai', 'Bangalore', 30, 1650.00, '06:00', '00:15', '18h 15m'),
('12504', 'Bangalore Mail', 'Mumbai', 'Bangalore', 30, 1680.00, '09:45', '04:30', '18h 45m'),
('12506', 'Bangalore Superfast', 'Mumbai', 'Bangalore', 30, 1700.00, '13:30', '08:30', '19h 00m'),
('12508', 'Karnataka Express', 'Mumbai', 'Bangalore', 30, 1630.00, '17:15', '12:30', '19h 15m'),
('12510', 'Overnight Express', 'Mumbai', 'Bangalore', 30, 1610.00, '21:45', '17:00', '19h 15m'),

-- ========== DELHI TO PUNE (4 trains) ==========
('12127', 'Pune Express', 'Delhi', 'Pune', 30, 1400.00, '07:30', '07:15', '23h 45m'),
('12129', 'Pune Superfast', 'Delhi', 'Pune', 30, 1450.00, '12:15', '12:30', '24h 15m'),
('12323', 'Pune Mail', 'Delhi', 'Pune', 30, 1380.00, '16:45', '17:30', '24h 45m'),
('12131', 'Pune Overnight', 'Delhi', 'Pune', 30, 1350.00, '21:30', '23:00', '25h 30m'),

-- ========== PUNE TO DELHI (4 trains) ==========
('12128', 'Delhi Express', 'Pune', 'Delhi', 30, 1450.00, '07:00', '07:15', '24h 15m'),
('12130', 'Delhi Superfast', 'Pune', 'Delhi', 30, 1480.00, '11:45', '12:30', '24h 45m'),
('12324', 'Delhi Mail', 'Pune', 'Delhi', 30, 1400.00, '16:00', '17:15', '25h 15m'),
('12132', 'Capitol Express', 'Pune', 'Delhi', 30, 1370.00, '20:30', '22:00', '25h 30m'),

-- ========== MUMBAI TO PUNE (6 trains) ==========
('12701', 'Deccan Queen', 'Mumbai', 'Pune', 30, 800.00, '05:45', '09:15', '3h 30m'),
('12703', 'Shivaji Express', 'Mumbai', 'Pune', 30, 820.00, '08:30', '12:15', '3h 45m'),
('12705', 'Pune Superfast', 'Mumbai', 'Pune', 30, 850.00, '11:45', '15:45', '4h 00m'),
('12707', 'Afternoon Express', 'Mumbai', 'Pune', 30, 830.00, '14:30', '18:45', '4h 15m'),
('12709', 'Evening Mail', 'Mumbai', 'Pune', 30, 810.00, '17:15', '21:30', '4h 15m'),
('12711', 'Night Express', 'Mumbai', 'Pune', 30, 790.00, '21:00', '01:30', '4h 30m'),

-- ========== PUNE TO MUMBAI (6 trains) ==========
('12702', 'Mumbai Express', 'Pune', 'Mumbai', 30, 850.00, '05:30', '09:30', '4h 00m'),
('12704', 'Mumbai Superfast', 'Pune', 'Mumbai', 30, 870.00, '08:45', '12:45', '4h 00m'),
('12706', 'CST Express', 'Pune', 'Mumbai', 30, 830.00, '11:30', '15:45', '4h 15m'),
('12708', 'Mumbai Mail', 'Pune', 'Mumbai', 30, 840.00, '14:15', '18:45', '4h 30m'),
('12710', 'Evening Superfast', 'Pune', 'Mumbai', 30, 860.00, '17:45', '22:00', '4h 15m'),
('12712', 'Mumbai Overnight', 'Pune', 'Mumbai', 30, 810.00, '21:30', '02:15', '4h 45m'),

-- ========== BANGALORE TO KOLKATA (4 trains) ==========
('12319', 'Howrah Express', 'Bangalore', 'Kolkata', 30, 1850.00, '07:00', '20:30', '37h 30m'),
('12511', 'Kolkata Mail', 'Bangalore', 'Kolkata', 30, 1820.00, '12:30', '02:45', '38h 15m'),
('12513', 'Howrah Superfast', 'Bangalore', 'Kolkata', 30, 1880.00, '17:15', '08:00', '38h 45m'),
('12515', 'Kolkata Overnight', 'Bangalore', 'Kolkata', 30, 1800.00, '21:45', '13:00', '39h 15m'),

-- ========== KOLKATA TO BANGALORE (4 trains) ==========
('12320', 'Bangalore Express', 'Kolkata', 'Bangalore', 30, 1900.00, '06:45', '20:30', '37h 45m'),
('12512', 'Bangalore Mail', 'Kolkata', 'Bangalore', 30, 1870.00, '11:30', '02:00', '38h 30m'),
('12514', 'Bangalore Superfast', 'Kolkata', 'Bangalore', 30, 1920.00, '16:45', '08:15', '39h 30m'),
('12516', 'Karnataka Express', 'Kolkata', 'Bangalore', 30, 1850.00, '21:00', '12:45', '39h 45m'),

-- ========== CHENNAI TO KOLKATA (4 trains) ==========
('12321', 'Chennai Rajdhani', 'Chennai', 'Kolkata', 30, 1750.00, '06:15', '13:30', '31h 15m'),
('12517', 'Kolkata Express', 'Chennai', 'Kolkata', 30, 1720.00, '11:45', '19:30', '31h 45m'),
('12519', 'Howrah Mail', 'Chennai', 'Kolkata', 30, 1780.00, '16:30', '00:45', '32h 15m'),
('12521', 'Kolkata Overnight', 'Chennai', 'Kolkata', 30, 1700.00, '21:00', '05:30', '32h 30m'),

-- ========== KOLKATA TO CHENNAI (4 trains) ==========
('12322', 'Coromandel Express', 'Kolkata', 'Chennai', 30, 1800.00, '06:30', '14:00', '31h 30m'),
('12518', 'Chennai Express', 'Kolkata', 'Chennai', 30, 1750.00, '11:15', '19:15', '32h 00m'),
('12520', 'Chennai Mail', 'Kolkata', 'Chennai', 30, 1820.00, '16:00', '00:30', '32h 30m'),
('12522', 'Madras Overnight', 'Kolkata', 'Chennai', 30, 1730.00, '20:45', '05:45', '33h 00m'),

-- ========== PUNE TO BANGALORE (4 trains) ==========
('12325', 'Bangalore Mail', 'Pune', 'Bangalore', 30, 1100.00, '07:30', '23:15', '15h 45m'),
('12523', 'Bangalore Express', 'Pune', 'Bangalore', 30, 1150.00, '12:00', '04:15', '16h 15m'),
('12525', 'Bangalore Superfast', 'Pune', 'Bangalore', 30, 1120.00, '16:45', '09:30', '16h 45m'),
('12527', 'Overnight Express', 'Pune', 'Bangalore', 30, 1080.00, '21:00', '14:15', '17h 15m'),

-- ========== BANGALORE TO PUNE (4 trains) ==========
('12326', 'Pune Express', 'Bangalore', 'Pune', 30, 1050.00, '07:15', '23:00', '15h 45m'),
('12524', 'Pune Mail', 'Bangalore', 'Pune', 30, 1120.00, '11:45', '04:00', '16h 15m'),
('12526', 'Pune Superfast', 'Bangalore', 'Pune', 30, 1100.00, '16:30', '09:30', '17h 00m'),
('12528', 'Pune Overnight', 'Bangalore', 'Pune', 30, 1070.00, '20:45', '13:45', '17h 00m'),

-- ========== CHENNAI TO PUNE (3 trains) ==========
('12529', 'Pune Express', 'Chennai', 'Pune', 30, 1300.00, '08:00', '06:45', '22h 45m'),
('12531', 'Pune Mail', 'Chennai', 'Pune', 30, 1350.00, '14:30', '13:45', '23h 15m'),
('12533', 'Pune Overnight', 'Chennai', 'Pune', 30, 1280.00, '20:15', '19:45', '23h 30m'),

-- ========== PUNE TO CHENNAI (3 trains) ==========
('12530', 'Chennai Express', 'Pune', 'Chennai', 30, 1320.00, '07:45', '06:30', '22h 45m'),
('12532', 'Chennai Mail', 'Pune', 'Chennai', 30, 1370.00, '13:30', '13:00', '23h 30m'),
('12534', 'Chennai Overnight', 'Pune', 'Chennai', 30, 1290.00, '19:45', '19:30', '23h 45m');
