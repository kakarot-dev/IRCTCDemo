import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.SimpleDateFormat;
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
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel searchTitle = new JLabel("Search Trains");
        searchTitle.setFont(new Font("Arial", Font.BOLD, 26));
        searchTitle.setForeground(PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        center.add(searchTitle, gbc);

        // Reset for form fields
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // From
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel fromLabel = new JLabel("From:");
        fromLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        center.add(fromLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> fromCombo = new JComboBox<>();
        fromCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        fromCombo.setPreferredSize(new Dimension(250, 32));
        try {
            for (String city : db.getAllCities()) fromCombo.addItem(city);
        } catch (Exception e) {
            e.printStackTrace();
        }
        center.add(fromCombo, gbc);

        // To
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel toLabel = new JLabel("To:");
        toLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        center.add(toLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> toCombo = new JComboBox<>();
        toCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        toCombo.setPreferredSize(new Dimension(250, 32));
        try {
            for (String city : db.getAllCities()) toCombo.addItem(city);
        } catch (Exception e) {
            e.printStackTrace();
        }
        center.add(toCombo, gbc);

        // Travel Date
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel dateLabel = new JLabel("Travel Date:");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        center.add(dateLabel, gbc);

        gbc.gridx = 1;
        final Date[] selectedDate = {new Date()};
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MMM-yyyy");

        JPanel datePickerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        datePickerPanel.setBackground(BG);

        JTextField dateField = new JTextField(displayFormat.format(selectedDate[0]), 10);
        dateField.setFont(new Font("Arial", Font.PLAIN, 14));
        dateField.setEditable(false);
        dateField.setBackground(Color.WHITE);

        JButton calendarBtn = new JButton("Calendar");
        calendarBtn.setFont(new Font("Arial", Font.BOLD, 12));
        calendarBtn.setPreferredSize(new Dimension(100, 32));
        calendarBtn.setFocusPainted(false);
        calendarBtn.setBackground(PRIMARY);
        calendarBtn.setForeground(Color.WHITE);
        calendarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        calendarBtn.addActionListener(e -> {
            CalendarDialog calDialog = new CalendarDialog(IRCTCApp.this, selectedDate[0]);
            calDialog.setVisible(true);
            selectedDate[0] = calDialog.getSelectedDate();
            dateField.setText(displayFormat.format(selectedDate[0]));
        });

        datePickerPanel.add(dateField);
        datePickerPanel.add(calendarBtn);
        center.add(datePickerPanel, gbc);

        // Search Button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton searchBtn = createButton("Search", PRIMARY);
        center.add(searchBtn, gbc);

        // Search Results Table
        gbc.gridy = 5;
        String[] cols = {"Train No", "Name", "Departure", "Duration", "Fare", "Seats"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(32);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setSelectionBackground(new Color(184, 207, 229));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(900, 200));
        center.add(scroll, gbc);

        // Action Buttons
        gbc.gridy = 6;
        JButton selectBtn = createButton("Book Train", SUCCESS);
        center.add(selectBtn, gbc);

        gbc.gridy = 7;
        JButton myBookingsBtn = createButton("My Bookings", ACCENT);
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
                            t.getJourneyDuration(),
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

                // Get date from picker and format for database
                SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
                String travelDate = dbFormat.format(selectedDate[0]);

                showSeatSelection(travelDate);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        myBookingsBtn.addActionListener(e -> showMyBookings());

        panel.add(center, BorderLayout.CENTER);
        mainPanel.add(panel, "dashboard");
    }

    private void showSeatSelection(String travelDate) {
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
                    (String)genderCombo.getSelectedItem(), selectedSeat[0], fare, travelDate);

                String type = Booking.isPremium(selectedSeat[0]) ? "Premium" : "Regular";
                showBookingConfirmation(dialog, travelDate, nameField.getText(),
                    ageField.getText(), (String)genderCombo.getSelectedItem(),
                    selectedSeat[0], type, (int)fare);
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
        p.add(new JLabel(text));
        return p;
    }

    private void showBookingConfirmation(JDialog parent, String date, String name,
                                         String age, String gender, int seat, String type, int fare) {
        JDialog confirmDialog = new JDialog(parent, "Booking Success", true);
        confirmDialog.setSize(550, 500);
        confirmDialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel title = new JLabel("✓ BOOKING CONFIRMED", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(SUCCESS);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        JTextArea details = new JTextArea(
            "Train: " + selectedTrain.getTrainName() + " (" + selectedTrain.getTrainNumber() + ")\n\n" +
            "Route: " + selectedTrain.getSource() + " → " + selectedTrain.getDestination() + "\n" +
            "Travel Date: " + date + "\nDeparture: " + selectedTrain.getDepartureTime() + "\n\n" +
            "Passenger: " + name + "\nAge: " + age + " | Gender: " + gender + "\n\n" +
            "Seat: " + seat + " (" + type + ")\nFare: Rs " + fare
        );
        details.setEditable(false);
        details.setBackground(BG);
        details.setFont(new Font("Arial", Font.PLAIN, 15));
        panel.add(details, BorderLayout.CENTER);

        JButton okBtn = createButton("OK", SUCCESS);
        okBtn.addActionListener(e -> confirmDialog.dispose());
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(BG);
        btnPanel.add(okBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        confirmDialog.add(panel);
        confirmDialog.setVisible(true);
        parent.dispose();
    }

    private void showMyBookings() {
        JDialog dialog = new JDialog(this, "My Bookings", true);
        dialog.setSize(1300, 600);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 13));

        String[] cols = {"ID", "Train", "Route", "Travel Date", "Departure", "Passenger", "Seat", "Type", "Fare", "Status"};

        // Get all bookings and separate by category
        List<Booking> upcomingBookings = new ArrayList<>();
        List<Booking> pastBookings = new ArrayList<>();
        List<Booking> cancelledBookings = new ArrayList<>();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();

            for (Booking b : db.getUserBookings(currentUser.getUserId())) {
                if (b.getStatus().equals("Cancelled")) {
                    cancelledBookings.add(b);
                } else {
                    try {
                        Date travelDate = dateFormat.parse(b.getTravelDate());
                        if (travelDate.after(today) || dateFormat.format(travelDate).equals(dateFormat.format(today))) {
                            upcomingBookings.add(b);
                        } else {
                            pastBookings.add(b);
                        }
                    } catch (Exception e) {
                        upcomingBookings.add(b); // Default to upcoming if date parse fails
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create tables for each tab
        JTable upcomingTable = createBookingsTable(cols, upcomingBookings, true);
        JTable pastTable = createBookingsTable(cols, pastBookings, false);
        JTable cancelledTable = createBookingsTable(cols, cancelledBookings, false);

        tabbedPane.addTab("Upcoming (" + upcomingBookings.size() + ")", new JScrollPane(upcomingTable));
        tabbedPane.addTab("Past (" + pastBookings.size() + ")", new JScrollPane(pastTable));
        tabbedPane.addTab("Cancelled (" + cancelledBookings.size() + ")", new JScrollPane(cancelledTable));

        panel.add(tabbedPane, BorderLayout.CENTER);

        JButton cancelBtn = createButton("Cancel Booking", ACCENT);
        JButton closeBtn = createButton("Close", PRIMARY);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(BG);
        btnPanel.add(cancelBtn);
        btnPanel.add(closeBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        cancelBtn.addActionListener(e -> {
            try {
                int selectedTab = tabbedPane.getSelectedIndex();
                JTable currentTable = selectedTab == 0 ? upcomingTable :
                                     selectedTab == 1 ? pastTable : cancelledTable;

                int row = currentTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(dialog, "Select booking first", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                DefaultTableModel model = (DefaultTableModel) currentTable.getModel();
                int id = (int)model.getValueAt(row, 0);
                String status = (String)model.getValueAt(row, 9);

                if (status.equals("Cancelled")) {
                    JOptionPane.showMessageDialog(dialog, "Already cancelled", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(dialog, "Cancel this booking?",
                    "Confirm", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    db.cancelBooking(id);
                    model.setValueAt("Cancelled", row, 9);
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

    private JTable createBookingsTable(String[] cols, List<Booking> bookings, boolean showGreen) {
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        for (Booking b : bookings) {
            model.addRow(new Object[]{
                b.getBookingId(),
                b.getTrainName(),
                b.getSource() + " to " + b.getDestination(),
                b.getTravelDate(),
                b.getDepartureTime(),
                b.getPassengerName(),
                b.getSeatNumber(),
                b.getSeatType(),
                "Rs " + (int)b.getFare(),
                b.getStatus()
            });
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        // Color-coded renderer
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    String status = (String) table.getValueAt(row, 9); // Status column
                    if (status.equals("Confirmed") && showGreen) {
                        c.setBackground(new Color(220, 255, 220)); // Light green
                        c.setForeground(new Color(0, 100, 0)); // Dark green text
                    } else if (status.equals("Cancelled")) {
                        c.setBackground(new Color(255, 220, 220)); // Light red
                        c.setForeground(new Color(139, 0, 0)); // Dark red text
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        });

        return table;
    }

    // Calendar Dialog for date selection
    private class CalendarDialog extends JDialog {
        private Date selectedDate;
        private Calendar calendar;
        private JLabel monthYearLabel;
        private JPanel daysPanel;

        public CalendarDialog(JFrame parent, Date initialDate) {
            super(parent, "Select Travel Date", true);
            this.selectedDate = initialDate != null ? initialDate : new Date();
            this.calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);

            setSize(600, 550);
            setLocationRelativeTo(parent);
            setResizable(false);

            JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
            mainPanel.setBackground(BG);
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Header with month/year navigation
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(PRIMARY);

            JButton prevBtn = new JButton("<");
            prevBtn.setFont(new Font("Arial", Font.BOLD, 24));
            prevBtn.setBackground(PRIMARY);
            prevBtn.setForeground(Color.WHITE);
            prevBtn.setFocusPainted(false);
            prevBtn.setBorderPainted(false);
            prevBtn.setPreferredSize(new Dimension(60, 50));

            JButton nextBtn = new JButton(">");
            nextBtn.setFont(new Font("Arial", Font.BOLD, 24));
            nextBtn.setBackground(PRIMARY);
            nextBtn.setForeground(Color.WHITE);
            nextBtn.setFocusPainted(false);
            nextBtn.setBorderPainted(false);
            nextBtn.setPreferredSize(new Dimension(60, 50));

            monthYearLabel = new JLabel("", JLabel.CENTER);
            monthYearLabel.setFont(new Font("Arial", Font.BOLD, 20));
            monthYearLabel.setForeground(Color.WHITE);

            headerPanel.add(prevBtn, BorderLayout.WEST);
            headerPanel.add(monthYearLabel, BorderLayout.CENTER);
            headerPanel.add(nextBtn, BorderLayout.EAST);

            // Days of week header
            JPanel weekHeader = new JPanel(new GridLayout(1, 7, 5, 5));
            weekHeader.setBackground(BG);
            String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
            for (String day : days) {
                JLabel label = new JLabel(day, JLabel.CENTER);
                label.setFont(new Font("Arial", Font.BOLD, 16));
                weekHeader.add(label);
            }

            // Days grid
            daysPanel = new JPanel(new GridLayout(6, 7, 8, 8));
            daysPanel.setBackground(BG);

            mainPanel.add(headerPanel, BorderLayout.NORTH);
            mainPanel.add(weekHeader, BorderLayout.CENTER);
            mainPanel.add(daysPanel, BorderLayout.SOUTH);

            add(mainPanel);

            // Navigation actions
            prevBtn.addActionListener(e -> {
                calendar.add(Calendar.MONTH, -1);
                updateCalendar();
            });

            nextBtn.addActionListener(e -> {
                calendar.add(Calendar.MONTH, 1);
                updateCalendar();
            });

            updateCalendar();
        }

        private void updateCalendar() {
            daysPanel.removeAll();

            SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy");
            monthYearLabel.setText(monthYearFormat.format(calendar.getTime()));

            Calendar tempCal = (Calendar) calendar.clone();
            tempCal.set(Calendar.DAY_OF_MONTH, 1);

            int firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK);
            int daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);

            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);

            // Empty cells before first day
            for (int i = 1; i < firstDayOfWeek; i++) {
                daysPanel.add(new JLabel(""));
            }

            // Day buttons
            for (int day = 1; day <= daysInMonth; day++) {
                tempCal.set(Calendar.DAY_OF_MONTH, day);
                Date btnDate = tempCal.getTime();

                JButton dayBtn = new JButton(String.valueOf(day));
                dayBtn.setFont(new Font("Arial", Font.BOLD, 18));
                dayBtn.setFocusPainted(false);
                dayBtn.setPreferredSize(new Dimension(65, 55));

                // Check if past date
                if (btnDate.before(today.getTime())) {
                    dayBtn.setEnabled(false);
                    dayBtn.setBackground(Color.LIGHT_GRAY);
                } else if (isSameDay(btnDate, new Date())) {
                    dayBtn.setBackground(new Color(173, 216, 230)); // Light blue for today
                } else if (isSameDay(btnDate, selectedDate)) {
                    dayBtn.setBackground(SUCCESS);
                    dayBtn.setForeground(Color.WHITE);
                } else {
                    dayBtn.setBackground(Color.WHITE);
                }

                final int selectedDay = day;
                dayBtn.addActionListener(e -> {
                    calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                    selectedDate = calendar.getTime();
                    dispose();
                });

                daysPanel.add(dayBtn);
            }

            daysPanel.revalidate();
            daysPanel.repaint();
        }

        private boolean isSameDay(Date date1, Date date2) {
            if (date1 == null || date2 == null) return false;
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);
            return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                   cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        }

        public Date getSelectedDate() {
            return selectedDate;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IRCTCApp());
    }
}
