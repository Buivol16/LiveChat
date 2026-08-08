package pl.denys.update_service.consumer.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.denys.update_service.event.chat.ChatCreatedEvent;
import pl.denys.update_service.model.chat.Chat;
import pl.denys.update_service.model.chat.PrivateChat;
import pl.denys.update_service.service.chat.ChatService;
import pl.denys.update_service.service.chat.PrivateChatService;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatCreatedConsumer {
    private final ChatService chatService;
    private final PrivateChatService privateChatService;
    private final StreamBridge streamBridge;

    @Bean(name = "chat-created")
    public Consumer<ChatCreatedEvent> chatCreated() {
        return event -> {
            log.info("Received ChatCreatedEvent with correlationId {}", event.getCorrelationId());
            var publicChat = event.getPublicChat();
            var privateChat = event.getPrivateChat();
            try {
                if (event.getIsPublicChat()){
                    createPublicChat(publicChat, event);
                }else {
                    createPrivateChat(privateChat, event);
                }
            } catch (RuntimeException e) {
                log.error("Error while creating new chat for correlation id {} {}", event.getCorrelationId(), e.getMessage());
            }
        };
    }

    private void createPublicChat(Chat chat, ChatCreatedEvent event) {
        var createdChat = chatService.createChat(chat);
        event.setPublicChat(createdChat);
        log.info("Chat has been successfully created {} for correlationId {}", createdChat.toString(), event.getCorrelationId());
        streamBridge.send("created_chat_notification", event);
        log.info("Notification has been sent");
    }

    private void createPrivateChat(PrivateChat chat, ChatCreatedEvent event) {
        var createdChat = privateChatService.createPrivateChat(chat);
        event.setPrivateChat(createdChat);
        log.info("PrivateChat has been successfully created {} for correlationId {}", createdChat.toString(), event.getCorrelationId());
        streamBridge.send("created_chat_notification", event);
        log.info("Notification has been sent");
    }
}
