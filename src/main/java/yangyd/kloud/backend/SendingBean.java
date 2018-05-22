package yangyd.kloud.backend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SendingBean {
  private final Source source;
  private final MessageChannel output;

  @Autowired
  public SendingBean(Source source,
                     @Qualifier("customOutput") MessageChannel output) {
    this.source = source;
    this.output = output;
  }

  public void sayHello(String name) {
    // both methods work
    source.output().send(MessageBuilder.withPayload(name).build());
    output.send(MessageBuilder.withPayload(name).build());
  }
}
