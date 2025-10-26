import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class IRCTCApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private User currentUser;
    private Train selectedTrain;
    private DatabaseManager db;

    private static final Color PRIMARY = new Color(0, 78, 137);
    private static final Color ACCENT = new Color(255, 107, 53);
    private static final Color SUCCESS = new Color(40, 167, 69);
    private static final Color PREMIUM_COLOR = new Color(255, 215, 0);
    private static final Color REGULAR_COLOR = new Color(144, 238, 144);
    private static final Color BG = new Color(245, 245, 245);

    public IRCTCApp() {
        db = DatabaseManager.getInstance();
        setTitle("IRCTC Railway Booking");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BG);

        createLoginPanel();
        createRegisterPanel();
        createDashboardPanel();

        add(mainPanel);
        cardLayout.show(mainPanel, "login");
        setVisible(true);
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(180, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

        JLabel title = new JLabel("IRCTC Login");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(20);
        passField.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        panel.add(passField, gbc);

        JButton loginBtn = createButton("Login", PRIMARY);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(loginBtn, gbc);

        JButton regBtn = createButton("Register", ACCENT);
        gbc.gridy = 4;
        panel.add(regBtn, gbc);

        loginBtn.addActionListener(e -> {
            try {
                currentUser = db.loginUser(nameField.getText(), new String(passField.getPassword()));
                if (currentUser != null) {
                    JOptionPane.showMessageDialog(this, "Welcome " + currentUser.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText("");
                    passField.setText("");
                    cardLayout.show(mainPanel, "dashboard");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid login", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        regBtn.addActionListener(e -> cardLayout.show(mainPanel, "register"));
        mainPanel.add(panel, "login");
    }

    private void createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

        JLabel title = new JLabel("Register Account");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(20);
        passField.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        panel.add(passField, gbc);

        JButton submitBtn = createButton("Register", SUCCESS);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(submitBtn, gbc);

        JButton backBtn = createButton("Back", PRIMARY);
        gbc.gridy = 4;
        panel.add(backBtn, gbc);

        submitBtn.addActionListener(e -> {
            try {
                if (db.nameExists(nameField.getText())) {
                    JOptionPane.showMessageDialog(this, "Name exists, use different name", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                db.registerUser(nameField.getText(), new String(passField.getPassword()));
                JOptionPane.showMessageDialog(this, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                nameField.setText("");
                passField.setText("");
                cardLayout.show(mainPanel, "login");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        mainPanel.add(panel, "register");
    }

    private void createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY);
        header.setPreferredSize(new Dimension(0, 55));
        JLabel title = new JLabel("  IRCTC Railway Booking", JLabel.LEFT);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);

        JButton logoutBtn = createButton("Logout", ACCENT);
        logoutBtn.setPreferredSize(new Dimension(100, 35));
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(PRIMARY);
        btnPanel.add(logoutBtn);
        header.add(btnPanel, BorderLayout.EAST);

        logoutBtn.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainPanel, "login");
        });

        panel.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel searchTitle = new JLabel("Search Trains");
        searchTitle.setFont(new Font("Arial", Font.BOLD, 26));
        searchTitle.setForeground(PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        center.add(searchTitle, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1;
        JLabel fromLabel = new JLabel("From:");
        fromLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        center.add(fromLabel, gbc);

        JComboBox<String> fromCombo = new JComboBox<>();
        fromCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        fromCombo.setPreferredSize(new Dimension(220, 32));
        try {
            for (String city : db.getAllCities()) fromCombo.addItem(city);
        } catch (Exception e) {
            e.printStackTrace();
        }
        gbc.gridx = 1;
        center.add(fromCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel toLabel = new JLabel("To:");
        toLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        center.add(toLabel, gbc);

        JComboBox<String> toCombo = new JComboBox<>();
        toCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        toCombo.setPreferredSize(new Dimension(220, 32));
        try {
            for (String city : db.getAllCities()) toCombo.addItem(city);
        } catch (Exception e) {
            e.printStackTrace();
        }
        gbc.gridx = 1;
        center.add(toCombo, gbc);

        JButton searchBtn = createButton("Search", PRIMARY);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        center.add(searchBtn, gbc);

        String[] cols = {"Train No", "Name", "Time", "Fare", "Seats"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(680, 160));
        gbc.gridy = 4;
        center.add(scroll, gbc);

        JButton selectBtn = createButton("Book Train", SUCCESS);
        gbc.gridy = 5;
        center.add(selectBtn, gbc);

        JButton myBookingsBtn = createButton("My Bookings", ACCENT);
        gbc.gridy = 6;
        center.add(myBookingsBtn, gbc);

        searchBtn.addActionListener(e -> {
            try {
                String from = (String)fromCombo.getSelectedItem();
                String to = (String)toCombo.getSelectedItem();
                if (from != null && to != null && !from.equals(to)) {
                    List<Train> trains = db.searchTrains(from, to);
                    model.setRowCount(0);
                    for (Train t : trains) {
                        model.addRow(new Object[]{
                            t.getTrainNumber(),
                            t.getTrainName(),
                            t.getDepartureTime(),
                            "Rs " + (int)t.getFare(),
                            db.getAvailableSeats(t.getTrainId())
                        });
                    }
                    if (trains.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No trains found", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Select different cities", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        selectBtn.addActionListener(e -> {
            try {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Select a train first", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String from = (String)fromCombo.getSelectedItem();
                String to = (String)toCombo.getSelectedItem();
                List<Train> trains = db.searchTrains(from, to);
                selectedTrain = trains.get(row);
                showSeatSelection();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        myBookingsBtn.addActionListener(e -> showMyBookings());

        panel.add(center, BorderLayout.CENTER);
        mainPanel.add(panel, "dashboard");
    }

    private void showSeatSelection() {
        JDialog dialog = new JDialog(this, "Select Seat - " + selectedTrain.getTrainName(), true);
        dialog.setSize(800, 680);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel legend = new JPanel(new FlowLayout());
        legend.setBackground(BG);
        legend.add(createLegend("Premium Rs " + (int)(selectedTrain.getFare() + Booking.PREMIUM_EXTRA), PREMIUM_COLOR));
        legend.add(createLegend("Regular Rs " + (int)selectedTrain.getFare(), REGULAR_COLOR));
        legend.add(createLegend("Booked", Color.RED));
        panel.add(legend, BorderLayout.NORTH);

        JPanel seatPanel = new JPanel(new GridLayout(5, 6, 10, 10));
        seatPanel.setBackground(BG);
        final int[] selectedSeat = {0};

        try {
            Set<Integer> booked = db.getBookedSeats(selectedTrain.getTrainId());
            for (int i = 1; i <= 30; i++) {
                int seat = i;
                JButton btn = new JButton(String.valueOf(i));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
                btn.setPreferredSize(new Dimension(80, 65));

                if (booked.contains(i)) {
                    btn.setBackground(Color.RED);
                    btn.setEnabled(false);
                } else if (Booking.isPremium(i)) {
                    btn.setBackground(PREMIUM_COLOR);
                } else {
                    btn.setBackground(REGULAR_COLOR);
                }

                btn.addActionListener(e -> {
                    selectedSeat[0] = seat;
                    for (Component c : seatPanel.getComponents()) {
                        if (c instanceof JButton) {
                            JButton b = (JButton)c;
                            if (b.isEnabled()) {
                                int num = Integer.parseInt(b.getText());
                                b.setBackground(Booking.isPremium(num) ? PREMIUM_COLOR : REGULAR_COLOR);
                            }
                        }
                    }
                    btn.setBackground(Color.BLUE);
                });

                seatPanel.add(btn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        panel.add(seatPanel, BorderLayout.CENTER);

        JPanel form = new JPanel(new FlowLayout());
        form.setBackground(BG);
        form.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(10);
        form.add(nameField);
        form.add(new JLabel("Age:"));
        JTextField ageField = new JTextField(3);
        form.add(ageField);
        form.add(new JLabel("Gender:"));
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        form.add(genderCombo);

        JButton confirmBtn = createButton("Confirm", SUCCESS);
        JButton cancelBtn = createButton("Cancel", ACCENT);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(BG);
        btnPanel.add(confirmBtn);
        btnPanel.add(cancelBtn);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(BG);
        bottom.add(form, BorderLayout.NORTH);
        bottom.add(btnPanel, BorderLayout.SOUTH);
        panel.add(bottom, BorderLayout.SOUTH);

        confirmBtn.addActionListener(e -> {
            if (selectedSeat[0] == 0) {
                JOptionPane.showMessageDialog(dialog, "Select a seat first", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                double fare = Booking.isPremium(selectedSeat[0]) ?
                    selectedTrain.getFare() + Booking.PREMIUM_EXTRA : selectedTrain.getFare();

                db.createBooking(currentUser.getUserId(), selectedTrain.getTrainId(),
                    nameField.getText(), Integer.parseInt(ageField.getText()),
                    (String)genderCombo.getSelectedItem(), selectedSeat[0], fare);

                String type = Booking.isPremium(selectedSeat[0]) ? "Premium" : "Regular";
                String confirmMsg = "BOOKING CONFIRMED\n\n" +
                    "Train: " + selectedTrain.getTrainName() + " (" + selectedTrain.getTrainNumber() + ")\n" +
                    "Route: " + selectedTrain.getSource() + " to " + selectedTrain.getDestination() + "\n" +
                    "Departure: " + selectedTrain.getDepartureTime() + "\n\n" +
                    "Passenger: " + nameField.getText() + "\n" +
                    "Age: " + ageField.getText() + " | Gender: " + genderCombo.getSelectedItem() + "\n\n" +
                    "Seat Number: " + selectedSeat[0] + " (" + type + ")\n" +
                    "Total Fare: Rs " + (int)fare;

                JOptionPane.showMessageDialog(dialog, confirmMsg, "Booking Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Booking failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private JPanel createLegend(String text, Color color) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBackground(BG);
        JLabel colorBox = new JLabel("   ");
        colorBox.setOpaque(true);
        colorBox.setBackground(color);
        colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        p.add(colorBox);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        p.add(label);
        return p;
    }

    private void showMyBookings() {
        JDialog dialog = new JDialog(this, "My Bookings", true);
        dialog.setSize(1100, 550);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] cols = {"ID", "Train", "Route", "Passenger", "Seat", "Type", "Fare", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        try {
            for (Booking b : db.getUserBookings(currentUser.getUserId())) {
                model.addRow(new Object[]{
                    b.getBookingId(),
                    b.getTrainName(),
                    b.getSource() + " to " + b.getDestination(),
                    b.getPassengerName(),
                    b.getSeatNumber(),
                    b.getSeatType(),
                    "Rs " + (int)b.getFare(),
                    b.getStatus()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);

        JButton cancelBtn = createButton("Cancel Booking", ACCENT);
        JButton closeBtn = createButton("Close", PRIMARY);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(BG);
        btnPanel.add(cancelBtn);
        btnPanel.add(closeBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        cancelBtn.addActionListener(e -> {
            try {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(dialog, "Select booking first", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int id = (int)model.getValueAt(row, 0);
                String status = (String)model.getValueAt(row, 7);
                if (status.equals("Cancelled")) {
                    JOptionPane.showMessageDialog(dialog, "Already cancelled", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(dialog, "Cancel this booking?",
                    "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    db.cancelBooking(id);
                    model.setValueAt("Cancelled", row, 7);
                    JOptionPane.showMessageDialog(dialog, "Booking cancelled", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        closeBtn.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IRCTCApp());
    }
}
