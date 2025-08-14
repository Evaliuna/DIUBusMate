import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;

public class DIUBusMateDashboard extends JFrame {

    private JLabel studentInfoLabel;
    private User currentUser; // Currently logged in user
    private JPanel mainContentPanel;
    private CardLayout cardLayout;

    public DIUBusMateDashboard(User loggedInUser) {
        this.currentUser = loggedInUser;

        setTitle("DIUBusMate Dashboard");
        setSize(1100, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(125, 96, 204));
        sidebar.setBounds(0, 0, 220, 600);
        sidebar.setLayout(null);

        JLabel logo = new JLabel("DIUBusMate");
        logo.setFont(new Font("SansSerif", Font.BOLD, 18));
        logo.setForeground(Color.WHITE);
        logo.setIcon(new ImageIcon(new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB)));
        logo.setBounds(25, 24, 180, 36);
        sidebar.add(logo);

        String[] menuText = {"Dashboard", "Transport Card", "Bus Schedule", "Complaint", "User", "Log Out"};
        int menuY = 90;
        for (String text : menuText) {
            JButton btn = new JButton(text);
            btn.setBounds(25, menuY, 170, 40);
            btn.setFocusPainted(false);
            btn.setBackground(text.equals("Dashboard") ? new Color(76, 0, 153) : new Color(117, 97, 184));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("SansSerif", Font.BOLD, 15));
            sidebar.add(btn);

            btn.addActionListener(e -> {
                if (btn.getText().equals("Log Out")) {
                    dispose();
                    new DIUBusMateLogin();
                } else if (btn.getText().equals("User")) {
                    UserInfoPage userInfoPage = new UserInfoPage(this, currentUser);
                    userInfoPage.setVisible(true);
                } else {
                    cardLayout.show(mainContentPanel, btn.getText());
                }
            });

            menuY += 52;
        }
        add(sidebar);

        // Main content area with card layout
        mainContentPanel = new JPanel();
        cardLayout = new CardLayout();
        mainContentPanel.setLayout(cardLayout);
        mainContentPanel.setBounds(220, 0, 880, 600);
        add(mainContentPanel);

        mainContentPanel.add(createDashboardPanel(), "Dashboard");
        mainContentPanel.add(createTransportCardPanel(), "Transport Card");
        mainContentPanel.add(createBusSchedulePanel(), "Bus Schedule");
        mainContentPanel.add(createComplaintPanel(), "Complaint");

        cardLayout.show(mainContentPanel, "Dashboard");

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(243, 240, 255));

        JLabel title = new JLabel("Student Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(85, 75, 141));
        title.setBounds(40, 20, 260, 35);
        panel.add(title);

        // Container for split layout
        JPanel container = new JPanel(new BorderLayout(30, 0));
        container.setBounds(40, 70, 800, 360);
        container.setBackground(new Color(243, 240, 255));
        panel.add(container);

        // Left side: Student info + Bus list
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(350, 360));
        leftPanel.setBackground(new Color(243, 240, 255));
        container.add(leftPanel, BorderLayout.WEST);

        // Student info label on top
        studentInfoLabel = new JLabel();
        studentInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        studentInfoLabel.setForeground(new Color(125, 96, 204));
        studentInfoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        studentInfoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(studentInfoLabel);

        leftPanel.add(Box.createVerticalStrut(50));

        // Bus list table
        String[] columns = {"Bus No", "Driver Name", "Bus Route"};
        Object[][] busData = {
                {"101", "Mr. Rahim", "North-1"},
                {"102", "Mr. Karim", "East-2"},
                {"103", "Mr. Salim", "South-3"},
                {"104", "Mr. Ahmed", "West-4"},
                {"105", "Ms. Fatima", "North-1"}
        };

        DefaultTableModel busTableModel = new DefaultTableModel(busData, columns) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable busTable = new JTable(busTableModel);
        busTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        busTable.setForeground(new Color(125, 96, 204));
        busTable.setRowHeight(25);
        busTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        busTable.getTableHeader().setForeground(new Color(180, 0, 100));

        JScrollPane busScrollPane = new JScrollPane(busTable);
        busScrollPane.setMaximumSize(new Dimension(350, 250));
        busScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(busScrollPane);


        // Right side: Weather and Date cards
        JPanel rightPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        rightPanel.setPreferredSize(new Dimension(420, 360));
        rightPanel.setBackground(new Color(243, 240, 255));
        container.add(rightPanel, BorderLayout.CENTER);

        // Weather Card with gradient and sun icon
        JPanel weatherCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 183, 77), 0, getHeight(), new Color(255, 222, 117));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        weatherCard.setLayout(new BorderLayout());
        weatherCard.setBorder(BorderFactory.createLineBorder(new Color(180, 0, 100), 2));
        rightPanel.add(weatherCard);

        JLabel sunIcon;
        try {
            sunIcon = new JLabel(new ImageIcon(getClass().getResource("/sun_icon.png")));
        } catch (Exception e) {
            sunIcon = new JLabel("☀");
            sunIcon.setFont(new Font("SansSerif", Font.BOLD, 40));
        }
        sunIcon.setBorder(BorderFactory.createEmptyBorder(10,15,10,10));
        weatherCard.add(sunIcon, BorderLayout.WEST);

        JLabel weatherInfo = new JLabel("<html><div style='color:#533A0E; font-size:18px;'>Dhaka<br>Sunny, 33°C</div></html>");
        weatherInfo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        weatherInfo.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
        weatherCard.add(weatherInfo, BorderLayout.CENTER);

        // Date Card
        JPanel dateCard = new JPanel(new BorderLayout());
        dateCard.setBackground(new Color(76, 0, 153));
        dateCard.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        rightPanel.add(dateCard);

        JLabel dateTitle = new JLabel("Date");
        dateTitle.setForeground(Color.WHITE);
        dateTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        dateCard.add(dateTitle, BorderLayout.NORTH);

        JLabel dateValue = new JLabel(LocalDate.now().toString());
        dateValue.setForeground(Color.WHITE);
        dateValue.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        dateCard.add(dateValue, BorderLayout.CENTER);

        updateStudentInfoLabel();

        return panel;
    }

    private JPanel createTransportCardPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(243, 240, 255));

        JLabel title = new JLabel("Transport Card");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(85, 75, 141));
        title.setBounds(30, 20, 300, 30);
        panel.add(title);

        // Card images container
        JPanel cardImagesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        cardImagesPanel.setBounds(100, 70, 680, 220);
        cardImagesPanel.setBackground(new Color(243, 240, 255));
        panel.add(cardImagesPanel);

        // Placeholder for front card image
        JLabel frontCardLabel = new JLabel();
        frontCardLabel.setPreferredSize(new Dimension(300, 180));
        frontCardLabel.setBorder(BorderFactory.createLineBorder(new Color(180, 0, 100), 1));
        frontCardLabel.setBackground(Color.WHITE);
        frontCardLabel.setOpaque(true);
        frontCardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frontCardLabel.setText("<html><center><b>Front Side</b><br>Card Image</center></html>");
        frontCardLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        cardImagesPanel.add(frontCardLabel);

        // Placeholder for back card image
        JLabel backCardLabel = new JLabel();
        backCardLabel.setPreferredSize(new Dimension(300, 180));
        backCardLabel.setBorder(BorderFactory.createLineBorder(new Color(180, 0, 100), 1));
        backCardLabel.setBackground(Color.WHITE);
        backCardLabel.setOpaque(true);
        backCardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backCardLabel.setText("<html><center><b>Back Side</b><br>Card Image</center></html>");
        backCardLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        cardImagesPanel.add(backCardLabel);

        // Info card below images
        JPanel infoCardPanel = new JPanel(new GridLayout(4,1, 0, 10));
        infoCardPanel.setBounds(220, 310, 440, 120);
        infoCardPanel.setBackground(Color.WHITE);
        infoCardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 100), 1),
                BorderFactory.createEmptyBorder(15,20,15,20)
        ));
        panel.add(infoCardPanel);

        JLabel cardNumberLabel = new JLabel("Card Number: 1001 2345 6789 0123");
        cardNumberLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        cardNumberLabel.setForeground(new Color(125, 96, 204));

        JLabel balanceLabel = new JLabel("Balance: $50");
        balanceLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel expiryLabel = new JLabel("Expiry Date: 12/24");
        expiryLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel statusLabel = new JLabel("Status: Active");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        statusLabel.setForeground(new Color(0, 128, 0)); // Green color

        infoCardPanel.add(cardNumberLabel);
        infoCardPanel.add(balanceLabel);
        infoCardPanel.add(expiryLabel);
        infoCardPanel.add(statusLabel);

        return panel;
    }

    private JPanel createBusSchedulePanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(243, 240, 255));

        JLabel title = new JLabel("Bus Schedule");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(85, 75, 141));
        title.setBounds(30, 20, 300, 30);
        panel.add(title);

        String[] columns = {"Route No", "Departure", "Arrival", "Stops", "Bus Type"};
        Object[][] data = {
                {"1", "06:00 AM", "07:15 AM", "City Center, University, Hospital", "Express"},
                {"2", "06:30 AM", "07:30 AM", "City Hall, Mall, School", "Regular"},
                {"3", "07:00 AM", "08:00 AM", "Bus Station, Museum, Airport", "Regular"},
                {"4", "07:30 AM", "08:30 AM", "Market, Cinema, Zoo", "Express"},
                {"5", "08:00 AM", "09:00 AM", "Harbor, Beach", "Scenic"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setForeground(new Color(180, 0, 100));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 70, 820, 450);
        panel.add(scrollPane);

        return panel;
    }

    private JPanel createComplaintPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(243, 240, 255));

        JLabel title = new JLabel("Complaint Management");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(85, 75, 141));
        title.setBounds(30, 20, 350, 30);
        panel.add(title);

        JLabel typeLabel = new JLabel("Complaint To:");
        typeLabel.setBounds(30, 70, 120, 25);
        panel.add(typeLabel);

        // Fixed complain recipient "Admin"
        JTextField adminField = new JTextField("Admin");
        adminField.setBounds(160, 70, 200, 30);
        adminField.setEditable(false);
        adminField.setBackground(new Color(230, 230, 230));
        panel.add(adminField);

        JLabel descLabel = new JLabel("Description:");
        descLabel.setBounds(30, 110, 120, 25);
        panel.add(descLabel);

        JTextArea descArea = new JTextArea();
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBounds(160, 110, 300, 100);
        panel.add(descScroll);

        JButton submitBtn = new JButton("Submit Complaint");
        submitBtn.setBounds(160, 220, 200, 35);
        submitBtn.setBackground(new Color(76, 0, 153));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        panel.add(submitBtn);

        String[] colNames = {"Complaint ID", "Type", "Status", "Date"};
        Object[][] complaintsData = {
                {"C001", "Admin", "Open", "2025-08-01"},
                {"C002", "Admin", "Resolved", "2025-07-20"},
                {"C003", "Admin", "In Progress", "2025-07-25"}
        };

        DefaultTableModel compModel = new DefaultTableModel(complaintsData, colNames) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable complaintTable = new JTable(compModel);
        complaintTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        complaintTable.setRowHeight(25);
        complaintTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        complaintTable.getTableHeader().setForeground(new Color(180, 0, 100));

        JScrollPane compScroll = new JScrollPane(complaintTable);
        compScroll.setBounds(500, 70, 350, 250);
        panel.add(compScroll);

        submitBtn.addActionListener(e -> {
            String desc = descArea.getText().trim();
            if (desc.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please enter complaint description.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String newID = "C" + String.format("%03d", complaintTable.getRowCount() + 1);
            compModel.addRow(new Object[]{newID, "Admin", "Open", LocalDate.now().toString()});
            JOptionPane.showMessageDialog(panel, "Complaint submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            descArea.setText("");
        });

        return panel;
    }

    public void updateStudentInfoLabel() {
        if (currentUser == null) {
            studentInfoLabel.setText("<html><div style='font-family:Segoe UI; font-size:16px; color:#7D60CC;'><b>No user logged in</b></div></html>");
            return;
        }
        String html = String.format("<html><div style='font-family:Segoe UI; font-size:16px; color:#7D60CC;'>"
                        + "<b>Hi! %s</b><br><br>ID: %s<br>Route: %s<br></div></html>",
                currentUser.getName(), currentUser.getId(), currentUser.getRoute());
        studentInfoLabel.setText(html);
    }

    public static class User {
        private String name;
        private String email;
        private String id;
        private String route;

        public User(String name, String email, String id, String route) {
            this.name = name;
            this.email = email;
            this.id = id;
            this.route = route;
        }

        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getId() { return id; }
        public String getRoute() { return route; }

        public void setName(String name) { this.name = name; }
        public void setEmail(String email) { this.email = email; }
        public void setId(String id) { this.id = id; }
        public void setRoute(String route) { this.route = route; }
    }

    public static void main(String[] args) {
        // Demo logged in user (replace with your actual login system)
        User demoUser = new User("John Doe", "john.doe@example.com", "12345", "North-1");
        SwingUtilities.invokeLater(() -> new DIUBusMateDashboard(demoUser));
    }
}
