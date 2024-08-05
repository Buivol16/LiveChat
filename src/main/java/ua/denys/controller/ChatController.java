package ua.denys.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.denys.db.facade.ChatFacade;
import ua.denys.db.facade.MessageFacade;
import ua.denys.exceptions.EntityNotFoundException;
import ua.denys.exceptions.WrongNameFormatException;
import ua.denys.model.ChatCreationInputDTO;
import ua.denys.model.ChatDTO;
import ua.denys.model.MessageDTO;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

  private final ChatFacade chatFacade;
  private final MessageFacade messageFacade;

  @MessageMapping("/chat/{chatId}")
  @SendTo("/messages/{chatId}")
  public MessageDTO sendMessage(
      @RequestBody MessageDTO messageDTO, @DestinationVariable("chatId") String chatId) {
    return messageFacade.registerMessage(messageDTO, chatId);
  }

  @PostMapping("/create-chat")
  public ChatDTO createChat(@RequestBody ChatCreationInputDTO inputDTO)
      throws EntityNotFoundException, WrongNameFormatException {
    return chatFacade.createChat(inputDTO);
  }

  @GetMapping("/chat-list")
  public List<ChatDTO> getChatList() {
    return chatFacade.getAvailableList();
  }

  @GetMapping("/chat/{id}")
  public ChatDTO getChat(@PathVariable Long id) {
    return chatFacade.findById(id);
  }
}
