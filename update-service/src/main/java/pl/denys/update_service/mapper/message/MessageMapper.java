package pl.denys.update_service.mapper.message;

import pl.denys.update_service.dto.message.MessageDTO;
import org.mapstruct.Mapper;
import pl.denys.update_service.model.message.Message;

@Mapper
public interface MessageMapper {

    Message messageDTOToMessage(MessageDTO messageDTO);

    MessageDTO messageToMessageDTO(Message message);
}
