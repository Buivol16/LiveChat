package pl.denys.update_service.consumer.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.denys.update_service.event.chat.ChatCreatedEvent;
import pl.denys.update_service.model.chat.Chat;
import pl.denys.update_service.service.ChatService;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatCreatedConsumer {
    private final ChatService chatService;

    @Bean(name = "chat-created")
    public Consumer<ChatCreatedEvent> chatCreated() {
        return event -> {
            log.info("Received ChatCreatedEvent with correlationId {}", event.getCorrelationId());
            var chat = event.getChat();
            chatService.createChat(chat);
        };
    }
}
