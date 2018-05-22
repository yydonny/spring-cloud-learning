package yangyd.kloud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class AuthenticatonConfig {
  @Bean
  DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  /**
   * Use the default JDBC based user details service.
   * The UserDetails object created is {@link org.springframework.security.core.userdetails.User}.
   * See its JavaDoc for details.
   */
  @Bean
  UserDetailsService userDetailsService(DataSource dataSource) {
    JdbcDaoImpl uds = new JdbcDaoImpl();
    uds.setDataSource(dataSource);
    return uds;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(16);
  }
}
