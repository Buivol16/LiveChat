package ua.denys.mappers;

import java.util.Objects;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.denys.db.model.Message;
import ua.denys.db.repositories.ChatRepository;
import ua.denys.db.repositories.ClientRepository;
import ua.denys.db.repositories.PrivateChatRepository;
import ua.denys.exceptions.EntityNotFoundException;
import ua.denys.model.MessageDTO;

@Mapper(
    componentModel = "Spring",
    imports = {Objects.class})
public interface MessageMapper {

  MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

  @Mapping(
      target = "chatId",
      expression =
          "java(Objects.isNull(message.getChat()) ? message.getPrivateChat().getId() : message.getChat().getId())")
  MessageDTO messageToMessageDTO(Message message);

  @Mapping(
      target = "chat",
      expression =
          "java(chatRepository\n"
              + ".findById(messageDTO.getChatId())\n"
              + ".orElseThrow(() -> new EntityNotFoundException(\"Chat is not found by id.\")))")
  @Mapping(
      target = "author",
      expression =
          "java(clientRepository\n"
              + ".findBySpecialId(messageDTO.getAuthor().getSpecialId())\n"
              + ".orElseThrow(() -> new EntityNotFoundException(\"Client is not found by id.\")))")
  @Mapping(source = "chatId", target = "privateChat", ignore = true)
  Message messageDTOToMessage(
      MessageDTO messageDTO,
      @Context ChatRepository chatRepository,
      @Context ClientRepository clientRepository)
      throws EntityNotFoundException;

  @Mapping(
      target = "privateChat",
      expression =
          "java(privateChatRepository\n"
              + ".findById(messageDTO.getChatId())\n"
              + ".orElseThrow(() -> new EntityNotFoundException(\"Chat is not found by id.\")))")
  @Mapping(
      target = "author",
      expression =
          "java(clientRepository\n"
              + ".findBySpecialId(messageDTO.getAuthor().getSpecialId())\n"
              + ".orElseThrow(() -> new EntityNotFoundException(\"Client is not found by id.\")))")
  @Mapping(source = "chatId", target = "chat", ignore = true)
  Message messageDTOToMessage(
      MessageDTO messageDTO,
      @Context PrivateChatRepository privateChatRepository,
      @Context ClientRepository clientRepository)
      throws EntityNotFoundException;
}
