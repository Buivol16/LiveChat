package pl.denys.mapper;

import org.mapstruct.Mapper;
import pl.denys.dto.chat.ChatDTO;
import pl.denys.model.chat.Chat;

@Mapper
public interface ChatMapper {
    Chat chatDTOToChat(ChatDTO chatDTO);
}
