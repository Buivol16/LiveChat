package ua.denys.db.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.denys.db.repositories.ChatRepository;
import ua.denys.db.repositories.ClientRepository;
import ua.denys.db.repositories.MessageRepository;
import ua.denys.db.repositories.PrivateChatRepository;
import ua.denys.exceptions.EntityNotFoundException;
import ua.denys.mappers.ChatMapper;
import ua.denys.mappers.ClientMapper;
import ua.denys.mappers.MessageMapper;
import ua.denys.model.MessageDTO;

@Component
@RequiredArgsConstructor
public class MessageFacade {
  private final MessageRepository messageRepository;
  private final ChatRepository chatRepository;
  private final PrivateChatRepository privateChatRepository;
  private final ClientRepository clientRepository;

  private static final MessageMapper messageMapper = MessageMapper.INSTANCE;
  private static final ClientMapper clientMapper = ClientMapper.INSTANCE;
  private static final ChatMapper chatMapper = ChatMapper.INSTANCE;

  @Transactional
  public MessageDTO registerMessage(MessageDTO messageDTO) throws EntityNotFoundException{
      var chatId = messageDTO.getChatId();
      var chat = chatRepository.findById(chatId);
      var privateChat = privateChatRepository.findById(chatId);
      var author = clientMapper.clientDTOToClient(messageDTO.getAuthor());
      var message = messageMapper.messageDTOToMessage(messageDTO, , author);
      message = messageRepository.save(message);
      return messageMapper.messageToMessageDTO(message);
  }
}
