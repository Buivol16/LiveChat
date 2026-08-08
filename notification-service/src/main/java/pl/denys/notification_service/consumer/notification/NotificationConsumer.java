package pl.denys.notification_service.consumer.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import pl.denys.notification_service.event.chat.ChatCreatedEvent;
import pl.denys.notification_service.event.message.MessageReadEvent;

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
                    log.info("Creating chat with id {} and correlationId {}", event.getChat().getId(), event.getCorrelationId());
                    handleChatCreatedEvent(event);
                } else if (event.getMessage() != null) {
                    log.info("Handling message creation of chat with id {} and correlationId {}", event.getMessage().getId(), event.getCorrelationId());
                    handleMessageCreatedEvent(event);
                }
            };
        } catch (Exception e) {
            log.error("Error while creating chat notification {}", e.getMessage());
            throw e;
        }
    }

    @Bean("message_read_event")
    public Consumer<MessageReadEvent> messageReadEventConsumer() {
        try {
            return this::handleMessageReadEvent;
        } catch (Exception e) {
            log.error("Error while sending read message event {}", e.getMessage());
            throw e;
        }
    }

    private void handleChatCreatedEvent(ChatCreatedEvent chatCreatedEvent) {
        var chatName = chatCreatedEvent.getChat().getName();
        var user = chatCreatedEvent.getChat().getCreator();
        log.info("Sending chat created event to specific user with correlation id {}", chatCreatedEvent.getCorrelationId());
        messagingTemplate.convertAndSendToUser(
                user, "/topic/notification", "Chat " + chatName + " has been created successfully");
    }

    private void handleMessageCreatedEvent(ChatCreatedEvent messageCreatedEvent) {
        var receiverId = messageCreatedEvent.getMessage().getReceiverId();
        var authorId = messageCreatedEvent.getMessage().getAuthorId();
        log.info("Sending message created event to specific user {} with correlation id {}", receiverId, messageCreatedEvent.getCorrelationId());
        messagingTemplate.convertAndSendToUser(receiverId, "/topic/notification", messageCreatedEvent);
        messagingTemplate.convertAndSendToUser(authorId, "/topic/notification", messageCreatedEvent);
    }

    private void handleMessageReadEvent(MessageReadEvent messageReadEvent) {
        var user = messageReadEvent.getReceiverUuid();
        log.info("Sending message read event to specific user {} with correlation id {}", user, messageReadEvent.getCorrelationId());
        user.forEach(uuid -> messagingTemplate.convertAndSendToUser(uuid, "/topic/message/read", messageReadEvent.getMessageIds()));
    }
}
