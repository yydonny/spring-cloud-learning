package yangyd.kloud.backend;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.RetryableStatusCodeException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import yangyd.kloud.model.BackendInquiryRequest;
import yangyd.kloud.service.LookupService;

@Component
public class BackendService {

  private final RestTemplate restTemplate;
  private final LookupService lookupService;

  @Autowired
  public BackendService(RestTemplate restTemplate, LookupService lookupService) {
    this.restTemplate = restTemplate;
    this.lookupService = lookupService;
  }

  @HystrixCommand(fallbackMethod = "inquiryFallback")
  public void inquiry(String userID) {
    BackendInquiryRequest request = new BackendInquiryRequest(userID);
    try {
      restTemplate.postForObject(lookupService.backendInquiry(), request, String.class);
    } catch (RestClientException e) {
      // HttpStatusCodeException will not be thrown if retry by load balance is used
      // and RetryableStatusCodeException doesn't contain response body
      Throwable t = e.getCause();
      if (t instanceof RetryableStatusCodeException) {
        throw new RuntimeException(t.getMessage());
      } else {
        throw e;
      }
    }
  }

  public void inquiryFallback(String userID) {
    System.err.println("request not made. backend not available");
  }

}
