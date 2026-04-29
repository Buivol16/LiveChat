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
        return event -> {
            var chatName = event.getChat().getName();
            var user = event.getChat().getCreator();
            messagingTemplate.convertAndSendToUser(user, "/topic/notification", "Chat " + chatName + " has been created successfully");
        };
    }
}
