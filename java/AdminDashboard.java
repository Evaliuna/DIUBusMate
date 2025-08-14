import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboard extends JFrame {

    private Admin currentAdmin;

    // Dashboard admin info UI components
    private JLabel hiLabelTop;
    private JLabel nameLabel, idLabel, designationLabel;
    private JTextField nameField, designationField;
    private JButton editBtn, saveBtn;
    private boolean isEditMode = false;

    private JPanel mainContentPanel;
    private CardLayout cardLayout;

    // User Management data
    private List<User> userList = new ArrayList<>();
    private DefaultTableModel userTableModel;
    private JTable userTable;

    private static final Color PURPLE_BLUE = new Color(76, 0, 153);

    public AdminDashboard(Admin admin) {
        this.currentAdmin = admin;

        // Sample users for demonstration
        userList.add(new User("student1", "Ali Reza", "ali.email@example.com", "0123456789", "Student"));
        userList.add(new User("student2", "Nabila Khan", "nabila@example.com", "0987654321", "Prefect"));

        setTitle("Admin Dashboard - DIUBusMate");
        setSize(1100, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        initSidebar();
        initMainContent();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(125, 96, 204));
        sidebar.setBounds(0, 0, 220, 600);
        sidebar.setLayout(null);

        JLabel logo = new JLabel("DIUBusMate Admin");
        logo.setFont(new Font("SansSerif", Font.BOLD, 18));
        logo.setForeground(Color.WHITE);
        logo.setBounds(25, 24, 180, 36);
        sidebar.add(logo);

        String[] menuText = {"Dashboard", "User Management", "Transport Card", "Bus Schedule", "Complaint", "Log Out"};

        int yPos = 90;
        for (String text : menuText) {
            JButton btn = new JButton(text);
            btn.setBounds(25, yPos, 170, 40);
            btn.setFocusPainted(false);
            btn.setBackground(text.equals("Dashboard") ? PURPLE_BLUE : new Color(117, 97, 184));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("SansSerif", Font.BOLD, 15));
            sidebar.add(btn);

            btn.addActionListener(e -> {
                if ("Log Out".equals(text)) {
                    dispose();
                    new DIUBusMateLogin(); // Implement or replace with your login window
                } else {
                    cardLayout.show(mainContentPanel, text);
                }
            });

            yPos += 52;
        }
        add(sidebar);
    }

    private void initMainContent() {
        mainContentPanel = new JPanel();
        cardLayout = new CardLayout();
        mainContentPanel.setLayout(cardLayout);
        mainContentPanel.setBounds(220, 0, 880, 600);
        add(mainContentPanel);

        mainContentPanel.add(createDashboardPanel(), "Dashboard");
        mainContentPanel.add(createUserManagementPanel(), "User Management");
        mainContentPanel.add(createTransportCardPanel(), "Transport Card");
        mainContentPanel.add(createBusSchedulePanel(), "Bus Schedule");
        mainContentPanel.add(createComplaintPanel(), "Complaint");

        cardLayout.show(mainContentPanel, "Dashboard");
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color top = new Color(169, 160, 233);
                Color bottom = PURPLE_BLUE;
                GradientPaint gp = new GradientPaint(0, 0, top, 0, getHeight(), bottom);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        JLabel title = new JLabel("Admin Dashboard");
        title.setBounds(40, 20, 260, 35);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        panel.add(title);

        // Greeting label "Hi, username" above the info card
        hiLabelTop = new JLabel("Hi, " + currentAdmin.getName());
        hiLabelTop.setFont(new Font("Segoe UI", Font.BOLD, 22));
        hiLabelTop.setForeground(PURPLE_BLUE);
        hiLabelTop.setBounds(40, 60, 350, 30);
        panel.add(hiLabelTop);

        // Admin info card below the greeting label
        JPanel adminInfoCard = createAdminInfoCard();
        adminInfoCard.setBounds(40, 100, 350, 209);
        panel.add(adminInfoCard);

        // Buttons under the info card, aligned right corner
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        buttonPanel.setBounds(55, 128 + 180, 350, 40);
        buttonPanel.setOpaque(false);
        panel.add(buttonPanel);

        editBtn = new JButton("Edit");
        saveBtn = new JButton("Save");
        saveBtn.setVisible(false);
        buttonPanel.add(editBtn);
        buttonPanel.add(saveBtn);

        editBtn.addActionListener(e -> toggleAdminEditMode(true));

        saveBtn.addActionListener(e -> {
            String newName = nameField.getText().trim();
            String newDesignation = designationField.getText().trim();

            if (newName.isEmpty() || newDesignation.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Designation cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            currentAdmin = new Admin(newName, currentAdmin.getId(), newDesignation);

            nameLabel.setText(newName);
            designationLabel.setText(newDesignation);
            hiLabelTop.setText("Hi, " + newName);

            toggleAdminEditMode(false);

            JOptionPane.showMessageDialog(this, "Admin info updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        // Left vertical stack below the button panel (for Map card)
        JPanel leftStackPanel = new JPanel();
        leftStackPanel.setLayout(new BoxLayout(leftStackPanel, BoxLayout.Y_AXIS));
        leftStackPanel.setBounds(40, 125 + 180 + 40 + 10, 350, 250);
        leftStackPanel.setOpaque(false);
        panel.add(leftStackPanel);

        JPanel mapCard = createMapCard();
        mapCard.setMaximumSize(new Dimension(350, 200));
        leftStackPanel.add(mapCard);

        // Right vertical stack: weather card above date card
        JPanel rightStackPanel = new JPanel();
        rightStackPanel.setLayout(new BoxLayout(rightStackPanel, BoxLayout.Y_AXIS));
        rightStackPanel.setBounds(410, 70, 420, 450);
        rightStackPanel.setOpaque(false);
        panel.add(rightStackPanel);

        JPanel weatherCard = createWeatherCard();
        weatherCard.setMaximumSize(new Dimension(420, 220));
        rightStackPanel.add(weatherCard);

        rightStackPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel dateCard = createDateCard();
        dateCard.setMaximumSize(new Dimension(420, 190));
        rightStackPanel.add(dateCard);

        return panel;
    }

    private JPanel createAdminInfoCard() {
        JPanel adminCard = new JPanel(new GridBagLayout());
        adminCard.setBackground(new Color(220, 215, 255));
        adminCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PURPLE_BLUE, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        adminCard.setPreferredSize(new Dimension(350, 180));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel userIcon = new JLabel("\uD83D\uDC64");  // user emoji
        userIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        userIcon.setForeground(PURPLE_BLUE);
        adminCard.add(userIcon, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy++;
        gbc.gridwidth = 1;

        // Name label and field
        JLabel labelName = new JLabel("Name :");
        labelName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelName.setForeground(PURPLE_BLUE);
        adminCard.add(labelName, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        nameLabel = new JLabel(currentAdmin.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        adminCard.add(nameLabel, gbc);

        nameField = new JTextField(currentAdmin.getName(), 15);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        nameField.setVisible(false);
        adminCard.add(nameField, gbc);

        // Admin Id (non-editable label)
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.gridy++;
        gbc.gridx = 0;

        JLabel labelId = new JLabel("Admin id :");
        labelId.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelId.setForeground(PURPLE_BLUE);
        adminCard.add(labelId, gbc);

        gbc.gridx = 1;
        idLabel = new JLabel(currentAdmin.getId());
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        adminCard.add(idLabel, gbc);

        // Designation label and field
        gbc.gridy++;
        gbc.gridx = 0;

        JLabel labelDesignation = new JLabel("Designation :");
        labelDesignation.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelDesignation.setForeground(PURPLE_BLUE);
        adminCard.add(labelDesignation, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        designationLabel = new JLabel(currentAdmin.getDesignation());
        designationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        adminCard.add(designationLabel, gbc);

        designationField = new JTextField(currentAdmin.getDesignation(), 15);
        designationField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        designationField.setVisible(false);
        adminCard.add(designationField, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;

        return adminCard;
    }

    private void toggleAdminEditMode(boolean editing) {
        isEditMode = editing;

        nameLabel.setVisible(!editing);
        designationLabel.setVisible(!editing);

        nameField.setVisible(editing);
        designationField.setVisible(editing);

        editBtn.setVisible(!editing);
        saveBtn.setVisible(editing);
    }

    private JPanel createMapCard() {
        JPanel mapCard = new JPanel(new BorderLayout());
        mapCard.setBackground(Color.WHITE);
        mapCard.setBorder(BorderFactory.createLineBorder(new Color(180, 0, 100), 2));
        mapCard.setPreferredSize(new Dimension(350, 250));

        ImageIcon mapIcon = null;
        try {
            mapIcon = new ImageIcon(getClass().getResource("/default_map.png"));
        } catch (Exception ignored) {
        }

        JLabel mapLabel = new JLabel();
        if (mapIcon != null) {
            mapLabel.setIcon(mapIcon);
        } else {
            mapLabel.setText("Map Image Not Found");
            mapLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mapLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            mapLabel.setForeground(Color.GRAY);
        }
        mapCard.add(mapLabel, BorderLayout.CENTER);

        return mapCard;
    }

    private JPanel createWeatherCard() {
        JPanel weatherCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 183, 77),
                        0, getHeight(), new Color(255, 222, 117));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        weatherCard.setLayout(new BorderLayout());
        weatherCard.setPreferredSize(new Dimension(420, 220));
        weatherCard.setBorder(BorderFactory.createLineBorder(new Color(180, 0, 100), 2));

        JLabel sunIcon = new JLabel("☀");
        sunIcon.setFont(new Font("SansSerif", Font.BOLD, 40));
        sunIcon.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        weatherCard.add(sunIcon, BorderLayout.WEST);

        JLabel weatherInfo = new JLabel("<html><div style='color:#533A0E; font-size:18px;'>Dhaka<br>Sunny, 33&#176;C</div></html>");
        weatherInfo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        weatherInfo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        weatherCard.add(weatherInfo, BorderLayout.CENTER);

        return weatherCard;
    }

    private JPanel createDateCard() {
        JPanel dateCard = new JPanel(new BorderLayout());
        dateCard.setPreferredSize(new Dimension(420, 190));
        dateCard.setBackground(PURPLE_BLUE);
        dateCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel dateTitle = new JLabel("Date");
        dateTitle.setForeground(Color.WHITE);
        dateTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        dateCard.add(dateTitle, BorderLayout.NORTH);

        JLabel dateValue = new JLabel(LocalDate.now().toString());
        dateValue.setForeground(Color.WHITE);
        dateValue.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        dateCard.add(dateValue, BorderLayout.CENTER);

        return dateCard;
    }

    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(243, 240, 255));

        JLabel title = new JLabel("User Management");
        title.setBounds(30, 15, 350, 30);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(85, 75, 141));
        panel.add(title);

        JButton addStudentBtn = new JButton("Add Student");
        addStudentBtn.setBounds(650, 15, 140, 30);
        addStudentBtn.setBackground(PURPLE_BLUE);
        addStudentBtn.setForeground(Color.WHITE);
        addStudentBtn.setFocusPainted(false);
        panel.add(addStudentBtn);

        String[] columns = {"Username", "Full Name", "Email", "Phone", "Designation", "Edit", "Delete"};

        userTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {
                return false; // Editing done via dialogs
            }
        };

        for (User user : userList) {
            userTableModel.addRow(new Object[]{
                    user.getUsername(), user.getFullName(), user.getEmail(), user.getPhone(), user.getDesignation(), "Edit", "Delete"
            });
        }

        userTable = new JTable(userTableModel);
        userTable.setRowHeight(30);
        userTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        userTable.getTableHeader().setForeground(new Color(180, 0, 100));

        userTable.getColumn("Edit").setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JButton btn = new JButton("Edit");
            btn.setForeground(Color.WHITE);
            btn.setBackground(PURPLE_BLUE);
            return btn;
        });

        userTable.getColumn("Edit").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            JButton btn = new JButton("Edit");

            {
                btn.setForeground(Color.WHITE);
                btn.setBackground(PURPLE_BLUE);
                btn.addActionListener(e -> {
                    int row = userTable.getSelectedRow();
                    if (row >= 0) {
                        User user = userList.get(row);
                        UserEditDialog dialog = new UserEditDialog(AdminDashboard.this, user, row);
                        dialog.setVisible(true);
                    }
                });
            }

            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return btn;
            }
        });

        userTable.getColumn("Delete").setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JButton btn = new JButton("Delete");
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(180, 0, 0));
            return btn;
        });

        userTable.getColumn("Delete").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            JButton btn = new JButton("Delete");

            {
                btn.setForeground(Color.WHITE);
                btn.setBackground(new Color(180, 0, 0));
                btn.addActionListener(e -> {
                    int row = userTable.getSelectedRow();
                    if (row >= 0) {
                        int confirm = JOptionPane.showConfirmDialog(AdminDashboard.this,
                                "Are you sure you want to delete this user?",
                                "Confirm Deletion",
                                JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            userList.remove(row);
                            userTableModel.removeRow(row);
                        }
                    }
                });
            }

            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return btn;
            }
        });

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(30, 60, 820, 480);
        panel.add(scrollPane);

        addStudentBtn.addActionListener(e -> {
            User newUser = new User("", "", "", "", "");
            userList.add(newUser);
            userTableModel.addRow(new Object[]{"", "", "", "", "", "Edit", "Delete"});
            int newRow = userTableModel.getRowCount() - 1;
            UserEditDialog dialog = new UserEditDialog(AdminDashboard.this, newUser, newRow);
            dialog.setVisible(true);
        });

        return panel;
    }

    private class UserEditDialog extends JDialog {
        private JTextField usernameField, fullNameField, emailField, phoneField, designationField;
        private JButton saveBtn;
        private User user;
        private int position;

        public UserEditDialog(Frame parent, User user, int position) {
            super(parent, "Edit User", true);
            this.user = user;
            this.position = position;

            setLayout(null);
            setSize(420, 360);
            setLocationRelativeTo(parent);

            addLabelAndField("Username:", user.getUsername(), 20, 20);
            addLabelAndField("Full Name:", user.getFullName(), 20, 70);
            addLabelAndField("Email:", user.getEmail(), 20, 120);
            addLabelAndField("Phone:", user.getPhone(), 20, 170);
            addLabelAndField("Designation:", user.getDesignation(), 20, 220);

            usernameField = (JTextField) getComponent(1);
            fullNameField = (JTextField) getComponent(3);
            emailField = (JTextField) getComponent(5);
            phoneField = (JTextField) getComponent(7);
            designationField = (JTextField) getComponent(9);

            saveBtn = new JButton("Save");
            saveBtn.setBackground(PURPLE_BLUE);
            saveBtn.setForeground(Color.WHITE);
            saveBtn.setFocusPainted(false);
            saveBtn.setBounds(150, 280, 100, 30);
            add(saveBtn);

            saveBtn.addActionListener(e -> {
                if (usernameField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Username cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                user.setUsername(usernameField.getText().trim());
                user.setFullName(fullNameField.getText().trim());
                user.setEmail(emailField.getText().trim());
                user.setPhone(phoneField.getText().trim());
                user.setDesignation(designationField.getText().trim());

                userList.set(position, user);
                userTableModel.setValueAt(user.getUsername(), position, 0);
                userTableModel.setValueAt(user.getFullName(), position, 1);
                userTableModel.setValueAt(user.getEmail(), position, 2);
                userTableModel.setValueAt(user.getPhone(), position, 3);
                userTableModel.setValueAt(user.getDesignation(), position, 4);

                JOptionPane.showMessageDialog(this, "User info saved successfully");
                dispose();
            });
        }

        private void addLabelAndField(String labelText, String fieldValue, int x, int y) {
            JLabel label = new JLabel(labelText);
            label.setBounds(x, y, 100, 25);
            add(label);

            JTextField field = new JTextField(fieldValue);
            field.setBounds(x + 110, y, 260, 25);
            add(field);
        }
    }

    private JPanel createTransportCardPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(243, 240, 255));

        JLabel title = new JLabel("Transport Cards");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(85, 75, 141));
        title.setBounds(30, 20, 300, 30);
        panel.add(title);

        String[] columns = {"Si No", "Student ID", "Card Number", "Status"};

        Object[][] data = {
                {"1", "12234", "1001 2345 6789 0123", "Active"},
                {"2", "12235", "1001 2345 6789 0456", "Expired"},
                {"3", "12236", "1001 2345 6789 0789", "Active"},
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            public boolean isCellEditable(int row, int col) {
                return col == 3;  // Only Status editable
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setForeground(new Color(180, 0, 100));

        String[] statuses = {"Active", "Expired", "Suspended"};
        JComboBox<String> comboBox = new JComboBox<>(statuses);
        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboBox));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 70, 820, 470);
        panel.add(scrollPane);

        return panel;
    }

    private JPanel createBusSchedulePanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(243, 240, 255));

        JLabel title = new JLabel("Manage Bus Schedule");
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
            public boolean isCellEditable(int row, int col) {
                return col != 0;  // Route No non-editable
            }
        };

        JTable busTable = new JTable(model);
        busTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        busTable.setRowHeight(25);
        busTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        busTable.getTableHeader().setForeground(new Color(180, 0, 100));

        JScrollPane scrollPane = new JScrollPane(busTable);
        scrollPane.setBounds(30, 70, 820, 470);
        panel.add(scrollPane);

        return panel;
    }

    private JPanel createComplaintPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(243, 240, 255));

        JLabel title = new JLabel("Manage Complaints");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(85, 75, 141));
        title.setBounds(30, 20, 350, 30);
        panel.add(title);

        String[] columns = {"Complaint ID", "Student ID", "Type", "Description", "Status", "Date"};

        Object[][] data = {
                {"C001", "12234", "Service", "Driver was late", "Open", "2025-08-01"},
                {"C002", "12235", "Vehicle", "Bus was crowded", "Resolved", "2025-07-20"},
                {"C003", "12236", "Route", "Schedule confusing", "In Progress", "2025-07-25"},
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            public boolean isCellEditable(int row, int col) {
                return col == 4;  // Only Status editable
            }
        };

        JTable complaintTable = new JTable(model);
        complaintTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        complaintTable.setRowHeight(25);
        complaintTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        complaintTable.getTableHeader().setForeground(new Color(180, 0, 100));

        String[] statuses = {"Open", "In Progress", "Resolved", "Closed"};
        JComboBox<String> comboBox = new JComboBox<>(statuses);
        complaintTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comboBox));

        JScrollPane scrollPane = new JScrollPane(complaintTable);
        scrollPane.setBounds(30, 70, 820, 470);
        panel.add(scrollPane);

        return panel;
    }

    // Data classes

    public static class User {
        private String username, fullName, email, phone, designation;

        public User(String username, String fullName, String email, String phone, String designation) {
            this.username = username;
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.designation = designation;
        }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getDesignation() { return designation; }
        public void setDesignation(String designation) { this.designation = designation; }
    }

    public static class Admin {
        private String name, id, designation;

        public Admin(String name, String id, String designation) {
            this.name = name;
            this.id = id;
            this.designation = designation;
        }

        public String getName() { return name; }
        public String getId() { return id; }
        public String getDesignation() { return designation; }
    }

    public static void main(String[] args) {
        Admin admin = new Admin("Alice Smith", "ADM001", "Administrator");
        SwingUtilities.invokeLater(() -> new AdminDashboard(admin));
    }
}
