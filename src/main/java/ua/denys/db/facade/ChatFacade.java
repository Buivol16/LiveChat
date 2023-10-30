package ua.denys.db.facade;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.denys.db.model.Chat;
import ua.denys.db.model.PrivateChat;
import ua.denys.db.repositories.ChatRepository;
import ua.denys.db.repositories.PrivateChatRepository;
import ua.denys.exceptions.ChatAlreadyCreatedException;
import ua.denys.exceptions.ChatNotFoundException;
import ua.denys.exceptions.EmptyStringException;
import ua.denys.exceptions.EntityNotFoundException;
import ua.denys.exceptions.WrongNameFormatException;
import ua.denys.mappers.ChatMapper;
import ua.denys.mappers.PrivateChatMapper;
import ua.denys.model.ChatDTO;
import ua.denys.model.ClientDTO;
import ua.denys.model.PrivateChatDTO;
import ua.denys.service.IdCreator;

@Component
@RequiredArgsConstructor
public class ChatFacade {
  private final ChatRepository chatRepository;
  private final PrivateChatRepository privateChatRepository;
  private final ClientFacade clientFacade;
  private final IdCreator idCreator;

  private static final ChatMapper chatMapper = ChatMapper.INSTANCE;
  private static final PrivateChatMapper privateChatMapper = PrivateChatMapper.INSTANCE;

  public ChatDTO findById(String id) throws ChatNotFoundException {
    var chat = chatRepository.findById(id);
    var privateChat = privateChatRepository.findById(id);

    if (chat.isPresent()) {
      return chatMapper.chatToChatDTO(chat.get());
    } else if (privateChat.isPresent()) {
      return chatMapper.privateChatToChatDTO(privateChat.get());
    } else throw new ChatNotFoundException("This chat is not exists.");
  }

  public ChatDTO createChat(String name, ClientDTO clientDTO)
      throws EmptyStringException, WrongNameFormatException {
    if (name.startsWith("#"))
      throw new WrongNameFormatException("Chat's name mustn't starts with '#' tag.");
    checkStringForBlank(name);
    if (checkClient(clientDTO)) {
      if (chatRepository.existsByName(name)) throw new ChatAlreadyCreatedException("Chat with this name is already created.");
      var id = idCreator.createId();
      if (isExists(id)) return createChat(name, clientDTO);
      var chat = Chat.builder().name(name).id(id).build();
      chatRepository.save(chat);
      return chatMapper.chatToChatDTO(chat);
    } else
      throw new EntityNotFoundException(
          "This user is not available. So, you can't create a new chat.");
  }

  public PrivateChatDTO createPrivateChat(String participantSpecialId, ClientDTO creatorDTO)
      throws EntityNotFoundException, WrongNameFormatException, ChatAlreadyCreatedException {
    if (!participantSpecialId.startsWith("#"))
      throw new WrongNameFormatException("Private chat's name must starts with '#' tag.");
    else participantSpecialId = participantSpecialId.replaceFirst("#", "");
    var participant = clientFacade.findBySpecialId(participantSpecialId);
    var creator = clientFacade.findBySpecialId(creatorDTO.getSpecialId());
    if (creator.getName().equals(creatorDTO.getName())) {
      if (privateChatRepository.findByCreatorAndParticipant(creator, participant).isPresent())
        throw new ChatAlreadyCreatedException("Chat with this id is already created.");
      var id = idCreator.createId();
      if (isExists(id)) return createPrivateChat(participantSpecialId, creatorDTO);
      var chat = PrivateChat.builder().creator(creator).participant(participant).id(id).build();
      privateChatRepository.save(chat);
      return privateChatMapper.privateChatToPrivateChatDTO(chat);
    } else
      throw new EntityNotFoundException(
          "This user is not available. So, you can't create a new chat.");
  }

  public List<ChatDTO> getAvailableList() {
    return chatRepository.findAll().stream().map(chatMapper::chatToChatDTO).toList();
  }

  public boolean isExists(String id) {
    try {
      return chatRepository.existsById(id);
    } catch (Exception e) {
      return false;
    }
  }

  private boolean checkClient(ClientDTO clientDTO) throws EntityNotFoundException {
    var clientName = clientDTO.getName();
    var clientSpecialId = clientDTO.getSpecialId();
    if (clientFacade.findBySpecialId(clientSpecialId).getName().equals(clientName)) return true;
    else return false;
  }

  private void checkStringForBlank(String name) throws EmptyStringException {
    var exceptionMsg = "The chat name parameter is blank";
    if (name == null) throw new EmptyStringException(exceptionMsg);
    Optional.of(name)
        .ifPresentOrElse(
            chatName -> {
              if (chatName.isBlank()) throw new EmptyStringException(exceptionMsg);
            },
            () -> new EmptyStringException(exceptionMsg));
  }
}
