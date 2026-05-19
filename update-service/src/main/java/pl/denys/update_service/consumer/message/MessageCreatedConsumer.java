package pl.denys.update_service.consumer.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.denys.update_service.event.message.MessageCreatedEvent;
import pl.denys.update_service.service.message.MessageService;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageCreatedConsumer {
  private final MessageService messageService;
  private final StreamBridge streamBridge;

  @Bean(name = "message-created")
  public Consumer<MessageCreatedEvent> messageCreated() {
    return event -> {
      log.info("Received MessageCreatedEvent with correlationId {}", event.getCorrelationId());
      var message = event.getMessage();
      try {
        var createdMessage = messageService.createMessage(message);
        event.setMessage(createdMessage);
        log.info(
            "Message has been successfully created {} for correlationId {}",
            createdMessage.toString(),
            event.getCorrelationId());
        streamBridge.send("created_chat_notification", event);
        log.info("Created message notification has been sent");
      } catch (RuntimeException e) {
        log.error(
            "Error while creating new message for correlation id {} {}",
            event.getCorrelationId(),
            e.getMessage());
      }
    };
  }
}
