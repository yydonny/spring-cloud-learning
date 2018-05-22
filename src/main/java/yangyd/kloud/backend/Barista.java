package yangyd.kloud.backend;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Barista {

  @Input("incomingOrders")
  SubscribableChannel orders();

  @Output
  MessageChannel hotDrinks();

  @Output
  MessageChannel coldDrinks();
}
