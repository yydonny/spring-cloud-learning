package yangyd.kloud.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * getters and setters must all present and be public
 */
@ConfigurationProperties(prefix = "lookup")
public class LookupProps {
  private LegacyHost legacyHost;

  public LegacyHost getLegacyHost() {
    return legacyHost;
  }

  public void setLegacyHost(LegacyHost legacyHost) {
    this.legacyHost = legacyHost;
  }

  public static class LegacyHost {
    private String appName;
    private String dataInquiry;

    public String getAppName() {
      return appName;
    }

    public void setAppName(String appName) {
      this.appName = appName;
    }

    public String getDataInquiry() {
      return dataInquiry;
    }

    public void setDataInquiry(String dataInquiry) {
      this.dataInquiry = dataInquiry;
    }
  }
}
