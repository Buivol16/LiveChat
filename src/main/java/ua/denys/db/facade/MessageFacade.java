package ua.denys.db.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.denys.db.repositories.ChatRepository;
import ua.denys.db.repositories.ClientRepository;
import ua.denys.db.repositories.MessageRepository;
import ua.denys.db.repositories.PrivateChatRepository;
import ua.denys.exceptions.EntityNotFoundException;
import ua.denys.mappers.MessageMapper;
import ua.denys.model.MessageDTO;

@Component
@RequiredArgsConstructor
public class MessageFacade {
  private final MessageRepository messageRepository;
  private final ChatRepository chatRepository;
  private final PrivateChatRepository privateChatRepository;
  private final ClientRepository clientRepository;

  private static final MessageMapper mapper = MessageMapper.INSTANCE;

  @Transactional
  public MessageDTO registerMessage(MessageDTO messageDTO) throws EntityNotFoundException{
    try {
      var message = mapper.messageDTOToMessage(messageDTO, chatRepository, clientRepository);
      message = messageRepository.save(message);
      return mapper.messageToMessageDTO(message);
    } catch (EntityNotFoundException e) {
      try {
        var message =
            mapper.messageDTOToMessage(messageDTO, privateChatRepository, clientRepository);
        message = messageRepository.save(message);
        return mapper.messageToMessageDTO(message);
      } catch (EntityNotFoundException e1) {
        throw e1;
      }
    }
  }
}
