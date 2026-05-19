package pl.denys.notification_service.consumer.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import pl.denys.notification_service.event.chat.ChatCreatedEvent;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {
  private final SimpMessagingTemplate messagingTemplate;

  @Bean("created_chat_notification")
  public Consumer<ChatCreatedEvent> createdChatNotification() {
    try {
      return event -> {
        if (event.getChat() != null) {
          handleChatCreatedEvent(event);
        } else if (event.getMessage() != null) {
          handleMessageCreatedEvent(event);
        }
      };
    } catch (Exception e) {
      log.error("Error while creating chat notification {}", e.getMessage());
      throw e;
    }
  }

  private void handleChatCreatedEvent(ChatCreatedEvent chatCreatedEvent) {
    var chatName = chatCreatedEvent.getChat().getName();
    var user = chatCreatedEvent.getChat().getCreator();
    messagingTemplate.convertAndSendToUser(
        user, "/topic/notification", "Chat " + chatName + " has been created successfully");
  }

  private void handleMessageCreatedEvent(ChatCreatedEvent messageCreatedEvent) {
    var encryptedMessage = messageCreatedEvent.getMessage().getEncryptedMessage();
    var user = messageCreatedEvent.getMessage().getAuthorId();
    messagingTemplate.convertAndSendToUser(user, "/topic/notification", encryptedMessage);
  }
}
