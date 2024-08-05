package ua.denys.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.denys.db.model.Chat;
import ua.denys.model.ChatDTO;

@Mapper(uses = {MessageMapper.class})
public interface ChatMapper {

  ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

  @Mapping(source = "messages", target = "messageDTOs")
  ChatDTO chatToChatDTO(Chat chat);
}
