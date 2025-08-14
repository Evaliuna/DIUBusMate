import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UserInfoPage extends JDialog {

    private final DIUBusMateDashboard.User user;
    private final JTextField fullNameField;
    private final JTextField emailField;
    private final JTextField idField;
    private final JTextField routeField;

    public UserInfoPage(JFrame parent, DIUBusMateDashboard.User currentUser) {
        super(parent, "Edit User Info", true);
        this.user = currentUser;

        setSize(400, 350);
        setLayout(null);
        setLocationRelativeTo(parent);

        JLabel profileIcon = new JLabel();
        profileIcon.setIcon(new ImageIcon(new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB)));
        profileIcon.setBounds(150, 10, 80, 80);
        add(profileIcon);

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(50, 110, 100, 25);
        add(nameLabel);

        fullNameField = new JTextField(user.getName());
        fullNameField.setBounds(150, 110, 200, 25);
        add(fullNameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 145, 100, 25);
        add(emailLabel);

        emailField = new JTextField(user.getEmail());
        emailField.setBounds(150, 145, 200, 25);
        add(emailField);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(50, 180, 100, 25);
        add(idLabel);

        idField = new JTextField(user.getId());
        idField.setBounds(150, 180, 200, 25);
        add(idField);

        JLabel routeLabel = new JLabel("Route:");
        routeLabel.setBounds(50, 215, 100, 25);
        add(routeLabel);

        routeField = new JTextField(user.getRoute());
        routeField.setBounds(150, 215, 200, 25);
        add(routeField);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(150, 260, 100, 30);
        saveBtn.setBackground(new Color(76, 0, 153));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        add(saveBtn);

        saveBtn.addActionListener(e -> {
            user.setName(fullNameField.getText().trim());
            user.setEmail(emailField.getText().trim());
            user.setId(idField.getText().trim());
            user.setRoute(routeField.getText().trim());

            if (parent instanceof DIUBusMateDashboard dashboard) {
                dashboard.updateStudentInfoLabel();
            }
            dispose();
        });
    }
}
