package bo.net.tigo.domain;

/**
 * Created by aralco on 10/22/14.
 */
public class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
        this.username = "username";
        this.password = "password";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
