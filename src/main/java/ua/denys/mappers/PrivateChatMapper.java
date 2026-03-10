package ua.denys.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.denys.db.model.PrivateChat;
import ua.denys.model.PrivateChatDTO;

@Mapper(uses = {MessageMapper.class})
public interface PrivateChatMapper {

  PrivateChatMapper INSTANCE = Mappers.getMapper(PrivateChatMapper.class);
  
  PrivateChatDTO privateChatToPrivateChatDTO(PrivateChat privateChat);
}
