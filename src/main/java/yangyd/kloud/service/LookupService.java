package yangyd.kloud.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(LookupProps.class)
public class LookupService {
  private final LookupProps props;

  public LookupService(LookupProps props) {
    this.props = props;
  }

  /**
   * Look up the base URL of given service. The actual lookup is done by Ribbon.<br>
   * Service name must be URI-compliant (only alphanumeric and hyphen, and specifically, NO underscore.)
   *
   * @param service service name
   * @return service URL
   */
  @Cacheable("services")
  private String lookup(String service) {
    return "http://" + service + "/";
  }

  public String backendInquiry() {
    LookupProps.LegacyHost host = props.getLegacyHost();
    return lookup(host.getAppName()) + host.getDataInquiry();
  }

}
