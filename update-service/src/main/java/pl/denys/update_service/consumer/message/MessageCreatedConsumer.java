package pl.denys.update_service.consumer.message;

import pl.denys.update_service.dto.message.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.denys.update_service.event.message.MessageCreatedEvent;
import pl.denys.update_service.event.message.MessageReadEvent;
import pl.denys.update_service.model.notification.NotificationType;
import pl.denys.update_service.service.message.MessageService;
import pl.denys.update_service.service.notification.NotificationService;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageCreatedConsumer {
  private final MessageService service;
  private final NotificationService notificationService;
  private final StreamBridge streamBridge;

  @Bean(name = "message-created")
  public Consumer<MessageCreatedEvent> messageCreated() {
    return event -> {
      log.info("Received MessageCreatedEvent with correlationId {}", event.getCorrelationId());
      var dto = event.getMessage();
      try {
        MessageDTO createdMessage;
        if (dto.getIsPrivateChat()){
          log.info("Creating Message for private chat with correlationId {}", event.getCorrelationId());
          createdMessage = service.createMessageForPrivateChat(dto);
        }else {
          //todo write up method for public chats
          log.info("Creating Message for public chat with correlationId {}", event.getCorrelationId());
          createdMessage = new MessageDTO();
        }
        var notification = notificationService.saveAsNotification(createdMessage.getId(), NotificationType.MESSAGE_CREATED);
        event.setMessage(createdMessage);
        event.setNotificationUuid(notification.getUuid());
        log.info(
            "Message has been successfully created {} for correlationId {}",
            createdMessage.toString(),
            event.getCorrelationId());
        streamBridge.send("created_chat_notification", event);
        log.info("Created dto notification has been sent");
      } catch (RuntimeException e) {
        log.error(
            "Error while creating new dto for correlation id {} {}",
            event.getCorrelationId(),
            e.getMessage());
        e.printStackTrace();
      }
    };
  }

  @Bean("message-read")
  public Consumer<MessageReadEvent> messageRead() {
    return event -> {
      log.info("Updating message read status in messages {} with correlationId {}", event.getMessageIds(), event.getCorrelationId());
      service.readMessages(event.getMessageIds());
      log.info("Message read status has been successfully updated in messages {} with correlationId {}", event.getMessageIds(), event.getCorrelationId());
    };
  }
}
