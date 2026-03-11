package pl.denys.update_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.denys.update_service.model.chat.Chat;
import pl.denys.update_service.repository.chat.ChatRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
  private final ChatRepository chatRepository;

  public Chat createChat(Chat chat) throws RuntimeException {
    try {
      log.info("Start creating chat with name {}", chat.getName());
      var newChat = chatRepository.save(chat);
      return newChat;
    } catch (RuntimeException e) {
      log.error("An exception happened while trying to create a new chat {}", e.getMessage());
      e.printStackTrace();
      throw e;
    }
  }
}
