package ua.denys.db.facade;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.denys.db.model.Chat;
import ua.denys.db.repository.ChatRepository;
import ua.denys.exceptions.ChatAlreadyCreatedException;
import ua.denys.exceptions.ChatNotFoundException;
import ua.denys.exceptions.EmptyStringException;
import ua.denys.exceptions.WrongNameFormatException;
import ua.denys.mapper.ChatMapper;
import ua.denys.model.ChatCreationInputDTO;
import ua.denys.model.ChatDTO;

@Component
@RequiredArgsConstructor
public class ChatFacade {
  private final ChatRepository chatRepository;
  private final ClientFacade clientFacade;

  private static final ChatMapper chatMapper = ChatMapper.INSTANCE;

  public ChatDTO findById(Long id) throws ChatNotFoundException {
    var chat =
        chatRepository
            .findById(id)
            .orElseThrow(() -> new ChatNotFoundException("This chat is not exists."));
    return chatMapper.chatToChatDTO(chat);
  }

  public ChatDTO createChat(ChatCreationInputDTO inputDTO)
      throws EmptyStringException, WrongNameFormatException {
    var name = inputDTO.getChatName();
    var clientSpecialId = inputDTO.getClientSpecialId();
    var client = clientFacade.findBySpecialId(clientSpecialId);
    checkStringForBlank(name);
    checkStringForBlank(clientSpecialId);
    checkChatNameExistingOrElseThrowException(name);
    if (inputDTO.getChatName().startsWith("#")) {
      var recipient = clientFacade.findBySpecialId(inputDTO.getChatName());
      var privateChat =
          Chat.builder().isPrivate(true).name(name).author(client).recipient(recipient).build();
      privateChat = chatRepository.save(privateChat);
      return chatMapper.chatToChatDTO(privateChat);
    } else {
      var publicChat = Chat.builder().isPrivate(false).name(name).author(client).build();
      publicChat = chatRepository.save(publicChat);
      return chatMapper.chatToChatDTO(publicChat);
    }
  }

  private boolean checkChatNameExistingOrElseThrowException(String name) {
    if (chatRepository.existsByName(name))
      throw new ChatAlreadyCreatedException("Chat with this name is already created.");
    return true;
  }

  public List<ChatDTO> getAvailableList() {
    return chatRepository.findAll().stream().map(chatMapper::chatToChatDTO).toList();
  }

  public boolean isExists(Long id) {
    try {
      return chatRepository.existsById(id);
    } catch (Exception e) {
      return false;
    }
  }

  private void checkStringForBlank(String name) throws EmptyStringException {
    var exceptionMsg = "The chat name parameter is blank";
    if (name == null || name.isBlank()) throw new EmptyStringException(exceptionMsg);
  }
}
