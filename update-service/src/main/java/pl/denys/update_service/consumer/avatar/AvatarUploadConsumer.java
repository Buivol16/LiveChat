package pl.denys.update_service.consumer.avatar;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.denys.update_service.event.avatar.AvatarChatUploadEvent;
import pl.denys.update_service.event.avatar.AvatarUploadEvent;
import pl.denys.update_service.service.avatar.AvatarService;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class AvatarUploadConsumer {
    private final AvatarService avatarService;

    @Bean(name = "avatar-upload")
    public Consumer<AvatarUploadEvent> avatarUpload(){
        return event -> {
            if (event.getUserId() == null) return;
            log.info("Avatar Upload Event for user: {}", event);
            avatarService.handleAvatarUpload(event);
            log.info("Avatar Upload Event for user Complete");
        };
    }

    @Bean(name = "avatar-chat-upload")
    public Consumer<AvatarChatUploadEvent> avatarChatUpload(){
        return event -> {
            if (event.getChatId() == null) return;
            log.info("Avatar Upload Event for chat: {}", event);
            avatarService.handleAvatarChatUpload(event);
            log.info("Avatar Upload for chat Complete");
        };
    }
}