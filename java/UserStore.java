import java.util.HashMap;
import java.util.Map;

public class UserStore {

    private static Map<String, User> users = new HashMap<>();

    public static class User {
        private String username;
        private String password;
        private String email;
        private String role;

        public User(String username, String password, String email, String role) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.role = role.toLowerCase();
        }

        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public String getEmail() { return email; }
        public String getRole() { return role; }

        public void setPassword(String password) { this.password = password; }
        public void setEmail(String email) { this.email = email; }
        public void setRole(String role) { this.role = role.toLowerCase(); }
    }

    public static boolean addUser(String username, String password, String email, String role) {
        if (users.containsKey(username.toLowerCase())) {
            return false; // user exists
        }
        users.put(username.toLowerCase(), new User(username, password, email, role));
        return true;
    }

    public static boolean validateUser(String username, String password) {
        User user = users.get(username.toLowerCase());
        return user != null && user.getPassword().equals(password);
    }

    public static User getUser(String username) {
        return users.get(username.toLowerCase());
    }
}
