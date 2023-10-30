package ua.denys.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import ua.denys.db.facade.ChatFacade;
import ua.denys.db.facade.ClientFacade;
import ua.denys.exceptions.EntityNotFoundException;
import ua.denys.exceptions.WrongNameFormatException;
import ua.denys.model.ChatCreationInputDTO;
import ua.denys.model.ChatDTO;
import ua.denys.model.ClientDTO;
import ua.denys.model.PrivateChatDTO;

@RestController
@RequiredArgsConstructor
public class CreatorController {

  private final ChatFacade chatFacade;
  private final ClientFacade clientFacade;

  private final SimpMessagingTemplate template;

  @PostMapping("/create-client")
  public ClientDTO createClient(String name) {
    return clientFacade.createClient(name);
  }

  @PostMapping("/create-chat")
  public ChatDTO createGlobalChat(@RequestBody ChatCreationInputDTO inputDTO)
      throws EntityNotFoundException, WrongNameFormatException {
    var chatDTO = chatFacade.createChat(inputDTO.getChatName(), inputDTO.getClientDTO());
    template.convertAndSend("/chats/list", chatDTO);
    return chatDTO;
  }

  @PostMapping("/create-private-chat")
  public PrivateChatDTO createPrivateChat(@RequestBody ChatCreationInputDTO inputDTO)
      throws EntityNotFoundException, WrongNameFormatException {
    var chatDTO = chatFacade.createPrivateChat(inputDTO.getChatName(), inputDTO.getClientDTO());
    if (chatDTO.getParticipant().getSpecialId().equals(chatDTO.getCreator().getSpecialId()))
      template.convertAndSend("/chats/private/" + chatDTO.getParticipant().getSpecialId(), chatDTO);
    else {
      template.convertAndSend("/chats/private/" + chatDTO.getParticipant().getSpecialId(), chatDTO);
      template.convertAndSend("/chats/private/" + chatDTO.getCreator().getSpecialId(), chatDTO);
    }
    return chatDTO;
  }
}
