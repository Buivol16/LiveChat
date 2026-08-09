package pl.denys.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import pl.denys.dto.message.MessageDTO;
import pl.denys.model.Message;

import java.util.List;

@Mapper
public interface MessageMapper {

    @Mappings({
            @Mapping(source = "message", target = "isPrivateChat", qualifiedByName = "mapIsPrivateIfPrivateOrPublic"),
            @Mapping(source = "message", target = "chatId", qualifiedByName = "mapChatIdIfPrivateOrPublic"),
            @Mapping(source = "message", target = "isMyMessage", qualifiedByName = "mapIsMyMessage")
    })
    MessageDTO messageToMessageDTO(Message message);

    List<MessageDTO> messagesToMessageDTO(List<Message> allPrivateChatMessages);

    @Named("mapChatIdIfPrivateOrPublic")
    default Long mapChatIdIfPrivateOrPublic(Message message) {
        if (message.getPrivateChatId() != null) {
            return message.getPrivateChatId();
        } else {
            return message.getPublicChatId();
        }
    }

    @Named("mapIsMyMessage")
    default Boolean mapIsMyMessage(Message message) {
        return message.getAuthenticationContextUuid().equals(message.getAuthorId());
    }

    @Named("mapIsPrivateIfPrivateOrPublic")
    default Boolean mapIsPrivateIfPrivateOrPublic(Message message) {
        return message.getPrivateChatId() != null;
    }
}
