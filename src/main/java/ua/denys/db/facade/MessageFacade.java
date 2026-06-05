package pl.denys.db.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.denys.db.model.Chat;
import pl.denys.db.model.Message;
import pl.denys.db.repository.ChatRepository;
import pl.denys.db.repository.ClientRepository;
import pl.denys.db.repository.MessageRepository;
import pl.denys.exceptions.EntityNotFoundException;
import pl.denys.mapper.MessageMapper;
import pl.denys.model.MessageDTO;

@Component
@RequiredArgsConstructor
public class MessageFacade {
  private final MessageRepository messageRepository;
  private final ChatRepository chatRepository;
  private final ClientRepository clientRepository;

  private static final MessageMapper mapper = MessageMapper.INSTANCE;

  @Transactional
  public MessageDTO registerMessage(MessageDTO messageDTO, String chatId)
      throws EntityNotFoundException {
    var isPrivate = isPrivateChat(messageDTO);
    var authorSpecialId = messageDTO.getAuthor().getSpecialId();
    var messageText = messageDTO.getText();
    var author =
        clientRepository
            .findBySpecialId(authorSpecialId)
            .orElseThrow(() -> throwAuthorNotFoundException(authorSpecialId));

    var chat = getPrivateOrPublicChat(messageDTO, isPrivate, authorSpecialId);

    var message = Message.builder().author(author).chat(chat).text(messageText).build();
    message = messageRepository.save(message);

    return mapper.messageToMessageDTO(message);
  }

  private Chat getPrivateOrPublicChat(
      MessageDTO messageDTO, boolean isPrivate, String authorSpecialId) {
    Chat chat;
    if (isPrivate) {
      chat =
          chatRepository
              .findByRecipientSpecialId(messageDTO.getChatId())
              .orElseThrow(() -> new EntityNotFoundException(authorSpecialId));
    } else {
      var chatIdLong = Long.parseLong(messageDTO.getChatId());
      chat =
          chatRepository
              .findById(chatIdLong)
              .orElseThrow(() -> throwChatNotFoundException(chatIdLong));
    }
    return chat;
  }

  private static EntityNotFoundException throwAuthorNotFoundException(String authorSpecialId) {
    return new EntityNotFoundException(
        String.format("The user with special id %s is not found", authorSpecialId));
  }

  private static EntityNotFoundException throwChatNotFoundException(Long id) {
    return new EntityNotFoundException(String.format("The chat with id %s is not found", id));
  }

  private static boolean isPrivateChat(MessageDTO messageDTO) {
    return messageDTO.getChatId().startsWith("#");
  }
}
