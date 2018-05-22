package yangyd.kloud.backend;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

public interface CustomSource extends Source {
  @Output("customOutput")
  MessageChannel output();
}
