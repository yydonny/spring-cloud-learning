package yangyd.kloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import yangyd.kloud.model.security.Authority;
import yangyd.kloud.model.security.AuthorityRepository;
import yangyd.kloud.model.security.User;
import yangyd.kloud.model.security.UserRepository;

@EntityScan(basePackages = "yangyd.kloud.model.security")
@Component
public class InitSecurityDB implements CommandLineRunner {
  private static final Logger logger = LoggerFactory.getLogger(InitSecurityDB.class);

  private final AuthorityRepository authorityRepository;
  private final UserRepository userRepository;
  private final PlatformTransactionManager transactionManager;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public InitSecurityDB(AuthorityRepository authorityRepository, UserRepository userRepository,
                                   PlatformTransactionManager transactionManager, PasswordEncoder passwordEncoder)
  {
    this.authorityRepository = authorityRepository;
    this.userRepository = userRepository;
    this.transactionManager = transactionManager;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) throws Exception {
    new TransactionTemplate(transactionManager).execute(transactionStatus -> {
      userRepository.save(new User("yangyd", passwordEncoder.encode("asdf1234")));
      authorityRepository.save(new Authority("yangyd", "ADMIN"));
      authorityRepository.save(new Authority("yangyd", "ACTUATOR")); // for Spring boot actuator
      logger.info("User {} ({}) registered.", "yangyd", "ADMIN");

      userRepository.save(new User("user1", passwordEncoder.encode("asdf1234")));
      authorityRepository.save(new Authority("user1", "USER"));
      logger.info("User {} ({}) registered.", "user1", "USER");
      return null;
    });
  }
}
