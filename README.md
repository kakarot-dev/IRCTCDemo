# IRCTC Railway Booking System

A comprehensive Java Swing-based railway ticket booking system with modern UI/UX, demonstrating Object-Oriented Programming principles and MySQL database integration.

## Features

### Core Functionality
- User registration and authentication system
- Advanced train search with date-based booking
- Interactive visual calendar for travel date selection
- Real-time seat availability with color-coded status
- Visual seat selection grid (Premium and Regular seats)
- Complete booking management (create, view, cancel)
- 230+ trains across 8+ major Indian cities with multiple daily timings

### User Experience
- Modern, intuitive GUI with professional color scheme
- Tabbed booking history (Upcoming, Past, Cancelled)
- Color-coded booking status indicators
- Comprehensive booking confirmation dialogs
- Journey duration and timing information
- Persistent data across application restarts

## Technologies Used

- **Language**: Java
- **GUI Framework**: Java Swing
- **Database**: MySQL
- **JDBC Driver**: MySQL Connector/J 9.5.0

## Project Structure

```
oops/
├── User.java              # User entity class
├── Train.java             # Train entity with timing details
├── Booking.java           # Booking entity with travel date
├── DatabaseManager.java   # Database operations (SQL abstraction)
├── IRCTCApp.java         # Main GUI application with calendar
├── schema.sql            # Database schema with 230+ trains
└── mysql-connector-j-9.5.0.jar
```

## OOP Concepts Demonstrated

- **Encapsulation**: Private fields with public getters/setters in all entity classes
- **Abstraction**: DatabaseManager abstracts all SQL operations
- **Singleton Pattern**: DatabaseManager uses singleton for connection management
- **Separation of Concerns**: Entity classes, database layer, and GUI clearly separated
- **Inner Classes**: CalendarDialog as inner class for date selection
- **Event-Driven Programming**: Action listeners for interactive GUI components

## Database Schema

### Tables
- **users**: user_id, name, password
- **trains**: train_id, train_number, train_name, source, destination, total_seats, fare, departure_time, arrival_time, journey_duration
- **bookings**: booking_id, user_id, train_id, passenger_name, age, gender, seat_number, fare, travel_date, booking_date, status

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
2. **Login**: Use your credentials to access the system
3. **Search Trains**:
   - Select source and destination cities
   - Choose travel date using the interactive calendar
   - Click Search to view available trains
4. **Book Ticket**:
   - Select a train from search results
   - Visual seat selection with color indicators:
     - Green = Available Premium (1-15)
     - Light Green = Available Regular (16-30)
     - Red = Already Booked
   - Enter passenger details (name, age, gender)
   - Confirm booking
5. **Manage Bookings**:
   - View bookings in organized tabs (Upcoming/Past/Cancelled)
   - Cancel bookings if needed
   - Color-coded status indicators for easy identification

## Seat Types & Pricing

- **Premium Seats (1-15)**: Base fare + Rs 500
- **Regular Seats (16-30)**: Base fare only

## Train Network

### Major Routes (230+ trains total)
- Delhi ↔ Mumbai (6 trains each way)
- Delhi ↔ Bangalore (5 trains each way)
- Mumbai ↔ Kolkata (5 trains each way)
- Chennai ↔ Delhi (5 trains each way)
- Bangalore ↔ Chennai (6 trains each way)
- Mumbai ↔ Pune (6 trains each way)
- Delhi ↔ Kolkata (6 trains each way)
- And many more intercity routes...

### Timing Variations
Each route offers multiple daily departures:
- Early Morning (05:00 - 07:00)
- Morning (08:00 - 11:00)
- Afternoon (12:00 - 15:00)
- Evening (16:00 - 19:00)
- Night (20:00 - 23:00)

## Key Features Highlights

- **230+ Trains** with realistic departure/arrival times and journey durations
- **Visual Calendar** for intuitive date selection
- **Tabbed Interface** for organized booking management
- **Color-Coded Status** for instant booking status recognition
- **Data Persistence** - all user data and bookings survive application restarts
- **Clean Architecture** - well-organized code following OOP principles

## Credits

Developed by Joel as an Object-Oriented Programming project demonstrating clean code architecture, modern UI/UX design, and comprehensive database integration.
