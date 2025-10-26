# IRCTC Railway Booking System

A Java Swing-based railway ticket booking system demonstrating Object-Oriented Programming concepts with MySQL database integration.

## Features

- User registration and login
- Train search by source and destination
- Visual seat selection (Premium and Regular seats)
- Booking management (create and cancel bookings)
- 60+ trains across major Indian cities
- Multiple train options for popular routes

## Technologies Used

- **Language**: Java
- **GUI Framework**: Java Swing
- **Database**: MySQL
- **JDBC Driver**: MySQL Connector/J 9.5.0

## Project Structure

```
oops/
├── User.java              # User entity class
├── Train.java             # Train entity class
├── Booking.java           # Booking entity class
├── DatabaseManager.java   # Database operations (SQL abstraction)
├── IRCTCApp.java         # Main GUI application
├── schema.sql            # Database schema and initial data
└── mysql-connector-j-9.5.0.jar
```

## OOP Concepts Demonstrated

- **Encapsulation**: Private fields with getters/setters in all entity classes
- **Abstraction**: DatabaseManager abstracts all SQL operations
- **Singleton Pattern**: DatabaseManager uses singleton for connection management
- **Separation of Concerns**: Entity classes, database layer, and GUI separated

## Database Schema

### Tables
- **users**: user_id, name, password
- **trains**: train_id, train_number, train_name, source, destination, total_seats, fare, departure_time
- **bookings**: booking_id, user_id, train_id, passenger_name, age, gender, seat_number, fare, booking_date, status

## Setup Instructions

### 1. Start MySQL
```bash
sudo service mysql start
```

### 2. Set MySQL Root Password
```bash
sudo mysql -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'joel2006'; FLUSH PRIVILEGES;"
```

### 3. Compile
```bash
javac -cp .:mysql-connector-j-9.5.0.jar *.java
```

### 4. Run
```bash
java -cp .:mysql-connector-j-9.5.0.jar IRCTCApp
```

## Usage

1. **Register**: Create a new account with name and password
2. **Login**: Use your credentials to login
3. **Search**: Select source and destination cities
4. **Book**: Choose a train, select seat (Premium: 1-15, Regular: 16-30), enter passenger details
5. **View Bookings**: Check your booking history
6. **Cancel**: Cancel bookings if needed

## Seat Types

- **Premium Seats (1-15)**: Base fare + Rs 500
- **Regular Seats (16-30)**: Base fare only

## Sample Routes

- Delhi ↔ Mumbai (4 trains each way)
- Delhi ↔ Bangalore (3 trains each way)
- Mumbai ↔ Kolkata (3 trains each way)
- Chennai ↔ Delhi (3 trains each way)
- And many more...

## Credits

Developed as an Object-Oriented Programming project demonstrating clean code architecture and database integration.
