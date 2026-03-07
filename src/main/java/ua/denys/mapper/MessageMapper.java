package pl.denys.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import pl.denys.db.model.Chat;
import pl.denys.db.model.Message;
import pl.denys.model.MessageDTO;

@Mapper(componentModel = "Spring")
public interface MessageMapper {

  MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

  @Mapping(source = "message.chat", target = "chatId", qualifiedByName = "setDTOChatId")
  MessageDTO messageToMessageDTO(Message message);

  @Named("setDTOChatId")
  default String setDTOChatId(Chat chat) {
    if (chat.isPrivate()) return chat.getRecipient().getSpecialId();
    return chat.getId().toString();
  }
}
