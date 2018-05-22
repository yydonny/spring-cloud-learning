package yangyd.kloud.backend;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

@EnableBinding(Source.class)
public class TimerSource {
  @Value("yyyy-MM-dd HH:mm:ss")
  private String format;

  @Bean
  @InboundChannelAdapter(value = Source.OUTPUT,
      poller = @Poller(fixedDelay = "${fixedDelay:2000}", maxMessagesPerPoll = "1"))
  public MessageSource<String> timerMessageSource() {
    return () -> new GenericMessage<>(new SimpleDateFormat(format).format(new Date()));
  }
}
