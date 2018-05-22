package yangyd.kloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.stereotype.Component;

@EnableWebSecurity
public class WebSecurityConfig {

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider);
  }

  /**
   * Security config based on form login.
   */
  @Component
  static class FormLogin extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests() // Access control. Order matters!
//          .mvcMatchers("/admin/**").hasAuthority("ADMIN")
//          .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')") // hasRole('ADMIN') means hasAuthority('ROLE_ADMIN')

          .mvcMatchers("/foo/**").permitAll()
          .anyRequest().authenticated()
          .and()

          // Stateless!!
          .httpBasic()
          .and()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
          .and()

          // enable form-based login http://docs.spring.io/spring-security/site/docs/4.1.4.RELEASE/reference/htmlsingle/#jc-form
          // Unauthorized request is redirected to /login; a POST to /login attempt to authenticate the user
          // CSRF is enabled by default
//          .formLogin()
//          .loginPage("/login").permitAll()
//          .and()

          // Handling logout
          // Post to /logout to logout, then redirect to /
          // CSRF is required as is for login
          .logout().logoutUrl("/logout").logoutSuccessUrl("/")
          .invalidateHttpSession(true)
          .deleteCookies("somecookie");
    }
  }
}
