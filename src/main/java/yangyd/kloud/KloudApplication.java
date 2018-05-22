package yangyd.kloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.metrics.atlas.AtlasTagProvider;
import org.springframework.cloud.netflix.metrics.atlas.EnableAtlas;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableZuulProxy
@EnableAtlas
public class KloudApplication {

  @LoadBalanced
  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  AtlasTagProvider atlasCommonTags(@Value("${spring.application.name}") String appName) {
    return () -> Collections.singletonMap("app", appName);
  }

  public static void main(String[] args) {
    SpringApplication.run(KloudApplication.class, args);
  }
}
