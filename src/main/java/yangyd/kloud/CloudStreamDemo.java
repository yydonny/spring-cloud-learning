package yangyd.kloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.router.ExpressionEvaluatingRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@EnableBinding
@Controller
public class CloudStreamDemo {
  @Autowired
  private BinderAwareChannelResolver resolver;

  /**
   * The destinations 'customers' and 'orders' are created in the broker (for example: exchange in case of
   * Rabbit or topic in case of Kafka) with the names 'customers' and 'orders', and the data is published to
   * the appropriate destinations.
   * Example:
   *   curl -H "Content-Type: application/json" -X POST -d "order-1" http://localhost:8080/orders
   */
  @RequestMapping(path = "/{target}", method = POST, consumes = "*/*")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void handleRequest(@RequestBody String body,
                            @PathVariable("target") String target,
                            @RequestHeader(HttpHeaders.CONTENT_TYPE) Object contentType) {
    sendMessage(body, target, contentType);
  }


  @RequestMapping(path = "/", method = POST, consumes = "application/json")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void handleRequest(@RequestBody String body, @RequestHeader(HttpHeaders.CONTENT_TYPE) Object contentType) {
    sendMessage(body, contentType);
  }

  @Bean(name = "routerChannel")
  MessageChannel routerChannel() {
    return new DirectChannel();
  }

  @Bean
  @ServiceActivator(inputChannel = "routerChannel")
  public ExpressionEvaluatingRouter router() {
    ExpressionEvaluatingRouter router =
        new ExpressionEvaluatingRouter(new SpelExpressionParser().parseExpression("payload.target"));
    router.setDefaultOutputChannelName("default-output");
    router.setChannelResolver(resolver);
    return router;
  }


  private void sendMessage(String body, String target, Object contentType) {
    resolver.resolveDestination(target).send(MessageBuilder.createMessage(body,
        new MessageHeaders(Collections.singletonMap(MessageHeaders.CONTENT_TYPE, contentType))));
  }

  private void sendMessage(Object body, Object contentType) {
    routerChannel().send(MessageBuilder.createMessage(body,
        new MessageHeaders(Collections.singletonMap(MessageHeaders.CONTENT_TYPE, contentType))));
  }
}
