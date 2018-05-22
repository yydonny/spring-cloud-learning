package yangyd.kloud.backend.reactive;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.reactive.FluxSender;
import org.springframework.cloud.stream.reactive.ObservableSender;
import reactor.core.publisher.Flux;
import rx.Observable;

@EnableBinding(Processor.class)
@EnableAutoConfiguration
public class UppercaseTransformer {

  // example of a simple Reactor-based Processor.

  @StreamListener
  @Output(Processor.OUTPUT)
  public Flux<String> receive1(@Input(Processor.INPUT) Flux<String> input) {
    return input.map(String::toUpperCase);
  }

  // The same processor using output arguments looks like this:

  @StreamListener
  public void receive2(@Input(Processor.INPUT) Flux<String> input,
                       @Output(Processor.OUTPUT) FluxSender output) {
    output.send(input.map(String::toUpperCase));
  }

  // in RxJava 1

  @StreamListener
  @Output(Processor.OUTPUT)
  public Observable<String> receive(@Input(Processor.INPUT) Observable<String> input) {
    return input.map(String::toUpperCase);
  }

  @StreamListener
  public void receive(@Input(Processor.INPUT) Observable<String> input,
                      @Output(Processor.OUTPUT) ObservableSender output) {
    output.send(input.map(String::toUpperCase));
  }

}
