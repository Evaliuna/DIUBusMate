import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DIUBusMateLogin extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton studentRadio;
    private JRadioButton adminRadio;
    private ButtonGroup roleGroup;

    public DIUBusMateLogin() {
        setTitle("DIUBusMate Login");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(30, 28, 62));
        leftPanel.setBounds(0, 0, 350, 700);
        leftPanel.setLayout(null);

        JLabel logo = new JLabel("DIUBusMate");
        logo.setFont(new Font("SansSerif", Font.BOLD, 18));
        logo.setForeground(Color.WHITE);
        logo.setBounds(30, 30, 200, 30);
        leftPanel.add(logo);

        JLabel avatar = new JLabel();
        avatar.setBounds(125, 90, 100, 100);
        avatar.setIcon(new ImageIcon(new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB)));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(avatar);

        JLabel userIcon = new JLabel("\uD83D\uDC64");
        userIcon.setFont(new Font("SansSerif", Font.PLAIN, 40));
        userIcon.setForeground(Color.WHITE);
        userIcon.setBounds(150, 120, 60, 60);
        leftPanel.add(userIcon);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameLabel.setBounds(70, 210, 210, 20);
        leftPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(70, 240, 210, 30);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        leftPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordLabel.setBounds(70, 280, 210, 20);
        leftPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(70, 310, 210, 30);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        leftPanel.add(passwordField);

        JLabel roleLabel = new JLabel("Login as:");
        roleLabel.setForeground(Color.WHITE);
        roleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        roleLabel.setBounds(70, 350, 70, 20);
        leftPanel.add(roleLabel);

        studentRadio = new JRadioButton("Student");
        studentRadio.setBounds(140, 350, 80, 20);
        studentRadio.setForeground(Color.WHITE);
        studentRadio.setBackground(new Color(30, 28, 62));
        studentRadio.setSelected(true);

        adminRadio = new JRadioButton("Admin");
        adminRadio.setBounds(230, 350, 80, 20);
        adminRadio.setForeground(Color.WHITE);
        adminRadio.setBackground(new Color(30, 28, 62));

        roleGroup = new ButtonGroup();
        roleGroup.add(studentRadio);
        roleGroup.add(adminRadio);
        leftPanel.add(studentRadio);
        leftPanel.add(adminRadio);

        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(70, 385, 210, 35);
        loginBtn.setBackground(new Color(196, 38, 133));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        leftPanel.add(loginBtn);

        JCheckBox rememberMe = new JCheckBox("Remember me");
        rememberMe.setForeground(Color.WHITE);
        rememberMe.setBackground(new Color(30, 28, 62));
        rememberMe.setBounds(190, 430, 160, 20);
        leftPanel.add(rememberMe);

        add(leftPanel);

        JPanel rightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                Color startColor = new Color(77, 43, 119);
                Color endColor = new Color(42, 219, 234);

                GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(new Color(196, 38, 133, 60));
                g2d.fillOval(getWidth() / 4, getHeight() / 4, 350, 150);

                g2d.setColor(new Color(42, 219, 234, 50));
                g2d.fillOval(getWidth() / 2, getHeight() / 2, 150, 100);

                g2d.dispose();
            }
        };
        rightPanel.setLayout(null);
        rightPanel.setBounds(350, 0, 600, 700);

        JLabel welcome = new JLabel("Welcome ^-^");
        welcome.setFont(new Font("SansSerif", Font.BOLD, 40));
        welcome.setForeground(Color.WHITE);
        welcome.setBounds(120, 90, 300, 50);
        rightPanel.add(welcome);

        JLabel info = new JLabel("<html>Enter your details to access DIUBusMate services.<br><br><br>Not a member?</html>");
        info.setFont(new Font("SansSerif", Font.PLAIN, 20));
        info.setForeground(Color.WHITE);
        info.setBounds(120, 200, 400, 120);
        rightPanel.add(info);

        JButton signUpBtn = new JButton("Sign up now");
        signUpBtn.setBounds(120, 350, 125, 40);
        signUpBtn.setFocusPainted(false);
        signUpBtn.setBackground(new Color(196, 38, 133));
        signUpBtn.setForeground(Color.WHITE);
        rightPanel.add(signUpBtn);

        add(rightPanel);

        loginBtn.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(DIUBusMateLogin.this,
                        "Please enter username and password.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (UserStore.validateUser(user, pass)) {
                JOptionPane.showMessageDialog(DIUBusMateLogin.this,
                        "Login successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                UserStore.User loggedIn = UserStore.getUser(user);
                dispose();

                if ("student".equalsIgnoreCase(loggedIn.getRole())) {
                    DIUBusMateDashboard.User dashboardUser = new DIUBusMateDashboard.User(
                            loggedIn.getUsername(),
                            loggedIn.getEmail(),
                            "ID12345", // TODO: Replace with actual user ID
                            "North-1"  // TODO: Replace with actual route
                    );
                    new DIUBusMateDashboard(dashboardUser);
                } else if ("admin".equalsIgnoreCase(loggedIn.getRole())) {
                    AdminDashboard.Admin admin = new AdminDashboard.Admin(
                            loggedIn.getUsername(), "ADM001", "Administrator"
                    );
                    new AdminDashboard(admin);
                } else {
                    JOptionPane.showMessageDialog(DIUBusMateLogin.this,
                            "User role not recognized.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(DIUBusMateLogin.this,
                        "Invalid username or password.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        signUpBtn.addActionListener(e -> {
            new SignUpForm();
            dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DIUBusMateLogin::new);
    }
}
