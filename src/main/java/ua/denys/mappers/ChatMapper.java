package ua.denys.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.denys.db.model.Chat;
import ua.denys.db.model.PrivateChat;
import ua.denys.model.ChatDTO;
import ua.denys.model.PrivateChatDTO;

@Mapper(uses = {MessageMapper.class, PrivateChatMapper.class})
public interface ChatMapper {

  ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

  @Mapping(
      source = "messages",
      target = "messageDTOs")
  ChatDTO chatToChatDTO(Chat chat);


  @Mapping(source = "messages", target = "messageDTOs")
  ChatDTO privateChatToChatDTO(PrivateChat privateChat);
}
