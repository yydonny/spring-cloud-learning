package yangyd.kloud.model.security;

import javax.persistence.*;

/**
 * Only used for populate demo data.
 * in application the user object is created by the UserDetailsService implementation.
 * In the case of Spring-boot's JdbcDaoImpl of UserDetailsService, it is org.springframework.security.core.userdetails.User
 */
@Entity
@Table(name = "users")
public class User {
  public User() {
  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.enabled = true;
  }

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private boolean enabled;

  public Long getId() {
    return id;
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

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}

