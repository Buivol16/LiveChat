package pl.denys.update_service.service.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.denys.update_service.model.chat.PrivateChat;
import pl.denys.update_service.repository.chat.PrivateChatRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateChatService {
    private final PrivateChatRepository privateChatRepository;

    public PrivateChat createPrivateChat(PrivateChat privateChat) throws RuntimeException {
        try {
            log.info("Start creating private chat by {} with {}", privateChat.getCreator(), privateChat.getPartnerId());
            var newChat = privateChatRepository.save(privateChat);
            return newChat;
        } catch (RuntimeException e) {
            log.error("An exception happened while trying to create a new chat {}", e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
