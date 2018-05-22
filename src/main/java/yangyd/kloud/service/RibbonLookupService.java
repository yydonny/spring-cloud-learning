package yangyd.kloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import java.net.URI;

/**
 * An alternative lookup service using Ribbon API directly
 */
public class RibbonLookupService {
  @Autowired
  private LoadBalancerClient loadBalancer;

  public void doStuff() {
    ServiceInstance instance = loadBalancer.choose("stores");
    URI storesUri = URI.create(String.format("http://%s:%s", instance.getHost(), instance.getPort()));
    // ... do something with the URI
  }
}
