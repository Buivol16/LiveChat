package ua.denys.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.denys.db.facade.ChatFacade;
import ua.denys.db.facade.MessageFacade;
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
  public MessageDTO actionChat(
      @RequestBody MessageDTO messageDTO, @DestinationVariable("chatId") String chatId)
      throws Exception {
    return messageFacade.registerMessage(messageDTO);
  }

  @GetMapping("/get-list")
  public List<ChatDTO> getList() {
    return chatFacade.getAvailableList();
  }

  @GetMapping("/get-chat/{id}")
  public ChatDTO getChat(@PathVariable String id) {
    return chatFacade.findById(id);
  }
}
