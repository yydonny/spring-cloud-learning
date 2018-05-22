package yangyd.kloud.web;

import com.netflix.spectator.api.Registry;
import com.netflix.spectator.api.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yangyd.kloud.backend.BackendService;
import yangyd.kloud.model.BackendInquiryRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class Bridge {
  private final BackendService backendService;

  // enabled by spring-cloud-spectator
  private final Registry registry;

  @Autowired
  public Bridge(BackendService backendService, Registry registry) {
    this.backendService = backendService;
    this.registry = registry;
  }

  @RequestMapping
  public String index(@RequestParam String user) {
    registry.counter("legacy-host", "route", "index").increment();
    backendService.inquiry(user);
    return "ok";
  }

  @RequestMapping(value = "/ted", method = RequestMethod.POST)
  public String ted(@RequestBody BackendInquiryRequest request) {
    Timer timer = registry.timer("legacy-host", "route", "ted");
    Long start = System.nanoTime();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    System.err.println(dateFormat.format(request.getDate()));
    timer.record(System.nanoTime() - start, TimeUnit.NANOSECONDS);
    return "ok";
  }
}
