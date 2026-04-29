package pl.denys.update_service.consumer.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.denys.update_service.event.chat.ChatCreatedEvent;
import pl.denys.update_service.service.ChatService;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatCreatedConsumer {
    private final ChatService chatService;
    private final StreamBridge streamBridge;

    @Bean(name = "chat-created")
    public Consumer<ChatCreatedEvent> chatCreated() {
        return event -> {
            log.info("Received ChatCreatedEvent with correlationId {}", event.getCorrelationId());
            var chat = event.getChat();
            try{
                var createdChat = chatService.createChat(chat);
                event.setChat(createdChat);
                log.info("Chat has been successfully createdChat {} for correlationId {}", createdChat.toString(), event.getCorrelationId());
                streamBridge.send("created_chat_notification", event);
                log.info("Notification has been sent");
            }catch (RuntimeException e){
                log.error("Error while creating new chat for correlation id {} {}", event.getCorrelationId(), e.getMessage());
            }
        };
    }
}
