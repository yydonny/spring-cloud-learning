package yangyd.kloud.model.security;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority {
  public Authority(String username, String authority) {
    this.username = username;
    this.authority = authority;
  }

  public Authority() {
  }

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String authority;

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }
}

