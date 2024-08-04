package ua.denys.mappers;

import java.util.Objects;

import jdk.jfr.Name;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ua.denys.db.model.Chat;
import ua.denys.db.model.Client;
import ua.denys.db.model.Message;
import ua.denys.db.repositories.ChatRepository;
import ua.denys.db.repositories.ClientRepository;
import ua.denys.db.repositories.PrivateChatRepository;
import ua.denys.exceptions.EntityNotFoundException;
import ua.denys.model.ClientDTO;
import ua.denys.model.MessageDTO;

@Mapper(
    componentModel = "Spring",
    imports = {Objects.class})
public abstract class MessageMapper {

  public static final MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

  @Mapping(target = "chatId", qualifiedByName = "getChatId", ignore = true)
  public abstract MessageDTO messageToMessageDTO(Message message);

  @Mapping(source = "chatId", target = "privateChat", ignore = true)
  public abstract Message messageDTOToMessage(
      MessageDTO messageDTO,
      Chat chat,
      Client author)
      throws EntityNotFoundException;

  @Named("getChatId")
  private String getChatId(Message message) {
    if (message.getChat() == null) return message.getPrivateChat().getId();
    else return message.getChat().getId();
  }

  @Named("getAuthor")
  private ClientDTO getAuthor(){

  }
}
