package pl.denys.update_service.service.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.denys.update_service.model.chat.Chat;
import pl.denys.update_service.model.message.Message;
import pl.denys.update_service.repository.chat.ChatRepository;
import pl.denys.update_service.repository.message.MessageRepository;

import java.sql.Timestamp;
import java.time.Clock;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {
  private final MessageRepository messageRepository;
  private final ChatRepository chatRepository;

  public Message createMessage(Message message) throws RuntimeException {
    try {
      log.info("Start creating message by {} user", message.getAuthorId());
      var chat = new Chat();
      chat.setId(message.getChatId());
      message.setChat(chat);
      message.setCreatedAt(Timestamp.from(Clock.systemUTC().instant()));
      var newMessage = messageRepository.save(message);
      return newMessage;
    } catch (RuntimeException e) {
      log.error("An exception happened while trying to create a new message {}", e.getMessage());
      e.printStackTrace();
      throw e;
    }
  }
}
