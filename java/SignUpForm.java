import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpForm extends JFrame {

    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField retypePasswordField;
    private JRadioButton studentRadio;
    private JRadioButton adminRadio;
    private ButtonGroup roleGroup;

    public SignUpForm() {
        setTitle("SignUp Form");
        setSize(400, 420);  // Increased height for role selection
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(219, 0, 114));  // Magenta-ish
        backgroundPanel.setLayout(new GridBagLayout());
        add(backgroundPanel);

        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(320, 360));
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(null);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 100), 1),
                BorderFactory.createEmptyBorder(10, 20, 20, 20)));

        backgroundPanel.add(formPanel);

        // Title
        JLabel titleLabel = new JLabel("SignUp Form");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(90, 10, 200, 30);
        formPanel.add(titleLabel);

        // Username, Email, Password fields (icons omitted for brevity)
        JLabel userIcon = new JLabel("\uD83D\uDC64");
        userIcon.setFont(new Font("SansSerif", Font.PLAIN, 18));
        userIcon.setBounds(10, 55, 25, 25);
        formPanel.add(userIcon);

        usernameField = new JTextField();
        usernameField.setToolTipText("Enter Username");
        usernameField.setBounds(40, 55, 240, 25);
        formPanel.add(usernameField);

        JLabel emailIcon = new JLabel("\u2709");
        emailIcon.setFont(new Font("SansSerif", Font.PLAIN, 18));
        emailIcon.setBounds(10, 90, 25, 25);
        formPanel.add(emailIcon);

        emailField = new JTextField();
        emailField.setToolTipText("Enter Email");
        emailField.setBounds(40, 90, 240, 25);
        formPanel.add(emailField);

        JLabel passIcon1 = new JLabel("\uD83D\uDD12");
        passIcon1.setFont(new Font("SansSerif", Font.PLAIN, 18));
        passIcon1.setBounds(10, 125, 25, 25);
        formPanel.add(passIcon1);

        passwordField = new JPasswordField();
        passwordField.setToolTipText("Create Password");
        passwordField.setBounds(40, 125, 240, 25);
        formPanel.add(passwordField);

        JLabel passIcon2 = new JLabel("\uD83D\uDD12");
        passIcon2.setFont(new Font("SansSerif", Font.PLAIN, 18));
        passIcon2.setBounds(10, 160, 25, 25);
        formPanel.add(passIcon2);

        retypePasswordField = new JPasswordField();
        retypePasswordField.setToolTipText("Retype Password");
        retypePasswordField.setBounds(40, 160, 240, 25);
        formPanel.add(retypePasswordField);

        // Role selection label
        JLabel roleLabel = new JLabel("Register as:");
        roleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        roleLabel.setForeground(Color.BLACK);
        roleLabel.setBounds(40, 195, 100, 25);
        formPanel.add(roleLabel);

        // Radio buttons for student/admin role
        studentRadio = new JRadioButton("Student");
        studentRadio.setBounds(140, 195, 100, 25);
        studentRadio.setSelected(true);
        studentRadio.setBackground(Color.WHITE);

        adminRadio = new JRadioButton("Admin");
        adminRadio.setBounds(240, 195, 100, 25);
        adminRadio.setBackground(Color.WHITE);

        roleGroup = new ButtonGroup();
        roleGroup.add(studentRadio);
        roleGroup.add(adminRadio);

        formPanel.add(studentRadio);
        formPanel.add(adminRadio);

        // Signup button
        JButton signupBtn = new JButton("SignUp");
        signupBtn.setBackground(new Color(76, 0, 153)); // purple color
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFocusPainted(false);
        signupBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        signupBtn.setBounds(40, 235, 240, 35);
        formPanel.add(signupBtn);

        // Action listener for signup button
        signupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());
                String retype = new String(retypePasswordField.getPassword());
                String role = studentRadio.isSelected() ? "student" : "admin";

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || retype.isEmpty()) {
                    JOptionPane.showMessageDialog(SignUpForm.this,
                            "All fields are required.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!password.equals(retype)) {
                    JOptionPane.showMessageDialog(SignUpForm.this,
                            "Passwords do not match.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (UserStore.addUser(username, password, email, role)) {
                    JOptionPane.showMessageDialog(SignUpForm.this,
                            "Account created successfully! Please log in.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();  // close sign up window
                    new DIUBusMateLogin();  // open login window
                } else {
                    JOptionPane.showMessageDialog(SignUpForm.this,
                            "Username already exists. Choose a different one.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUpForm::new);
    }
}
