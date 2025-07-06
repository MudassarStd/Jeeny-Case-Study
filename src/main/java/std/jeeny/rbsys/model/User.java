package std.jeeny.rbsys.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;


    public User() {}

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = UserRole.valueOf(role);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role.toString();
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

}

