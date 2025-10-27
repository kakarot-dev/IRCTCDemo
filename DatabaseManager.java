import java.io.*;
import java.sql.*;
import java.util.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "irctc_db";
    private static final String USER = "root";
    private static final String PASS = "joel2006";

    private static DatabaseManager instance;
    private Connection conn;

    private DatabaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.createStatement().executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            conn.close();

            conn = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASS);
            executeSQLFile("schema.sql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) instance = new DatabaseManager();
        return instance;
    }

    private void executeSQLFile(String filename) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringBuilder sql = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().startsWith("--") || line.trim().isEmpty()) continue;
            sql.append(line).append(" ");
            if (line.trim().endsWith(";")) {
                conn.createStatement().executeUpdate(sql.toString());
                sql = new StringBuilder();
            }
        }
        br.close();
    }

    // User operations (name is used as username)
    public User loginUser(String name, String password) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE name=? AND password=?");
        ps.setString(1, name);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            User u = new User();
            u.setUserId(rs.getInt("user_id"));
            u.setName(rs.getString("name"));
            return u;
        }
        return null;
    }

    public boolean registerUser(String name, String password) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO users (name, password) VALUES (?, ?)");
        ps.setString(1, name);
        ps.setString(2, password);
        ps.executeUpdate();
        return true;
    }

    public boolean nameExists(String name) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE name=?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        return rs.next() && rs.getInt(1) > 0;
    }

    // Train operations
    public List<Train> searchTrains(String source, String dest) throws SQLException {
        List<Train> trains = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM trains WHERE source=? AND destination=?");
        ps.setString(1, source);
        ps.setString(2, dest);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Train t = new Train();
            t.setTrainId(rs.getInt("train_id"));
            t.setTrainNumber(rs.getString("train_number"));
            t.setTrainName(rs.getString("train_name"));
            t.setSource(rs.getString("source"));
            t.setDestination(rs.getString("destination"));
            t.setTotalSeats(rs.getInt("total_seats"));
            t.setFare(rs.getDouble("fare"));
            t.setDepartureTime(rs.getString("departure_time"));
            t.setArrivalTime(rs.getString("arrival_time"));
            t.setJourneyDuration(rs.getString("journey_duration"));
            trains.add(t);
        }
        return trains;
    }

    public List<String> getAllCities() throws SQLException {
        List<String> cities = new ArrayList<>();
        ResultSet rs = conn.createStatement().executeQuery(
            "SELECT DISTINCT source FROM trains UNION SELECT DISTINCT destination FROM trains ORDER BY 1");
        while (rs.next()) cities.add(rs.getString(1));
        return cities;
    }

    public int getAvailableSeats(int trainId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM bookings WHERE train_id=? AND status='Confirmed'");
        ps.setInt(1, trainId);
        ResultSet rs = ps.executeQuery();
        return rs.next() ? 30 - rs.getInt(1) : 30;
    }

    // Booking operations
    public Set<Integer> getBookedSeats(int trainId) throws SQLException {
        Set<Integer> seats = new HashSet<>();
        PreparedStatement ps = conn.prepareStatement("SELECT seat_number FROM bookings WHERE train_id=? AND status='Confirmed'");
        ps.setInt(1, trainId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) seats.add(rs.getInt(1));
        return seats;
    }

    public boolean createBooking(int userId, int trainId, String name, int age, String gender, int seat, double fare, String travelDate) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO bookings (user_id, train_id, passenger_name, age, gender, seat_number, fare, travel_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setInt(1, userId);
        ps.setInt(2, trainId);
        ps.setString(3, name);
        ps.setInt(4, age);
        ps.setString(5, gender);
        ps.setInt(6, seat);
        ps.setDouble(7, fare);
        ps.setString(8, travelDate);
        ps.executeUpdate();
        return true;
    }

    public List<Booking> getUserBookings(int userId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement(
            "SELECT b.*, t.train_name, t.train_number, t.source, t.destination, t.departure_time " +
            "FROM bookings b JOIN trains t ON b.train_id = t.train_id WHERE b.user_id=? ORDER BY b.travel_date DESC, b.booking_date DESC");
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Booking b = new Booking();
            b.setBookingId(rs.getInt("booking_id"));
            b.setUserId(rs.getInt("user_id"));
            b.setTrainId(rs.getInt("train_id"));
            b.setPassengerName(rs.getString("passenger_name"));
            b.setAge(rs.getInt("age"));
            b.setGender(rs.getString("gender"));
            b.setSeatNumber(rs.getInt("seat_number"));
            b.setFare(rs.getDouble("fare"));
            b.setTravelDate(rs.getString("travel_date"));
            b.setBookingDate(rs.getString("booking_date"));
            b.setStatus(rs.getString("status"));
            b.setSeatType(b.getSeatNumber() <= 15 ? "Premium" : "Regular");
            b.setTrainName(rs.getString("train_name"));
            b.setSource(rs.getString("source"));
            b.setDestination(rs.getString("destination"));
            b.setDepartureTime(rs.getString("departure_time"));
            bookings.add(b);
        }
        return bookings;
    }

    public boolean cancelBooking(int bookingId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE bookings SET status='Cancelled' WHERE booking_id=?");
        ps.setInt(1, bookingId);
        ps.executeUpdate();
        return true;
    }
}
