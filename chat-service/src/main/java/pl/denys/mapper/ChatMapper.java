package pl.denys.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import pl.denys.dto.chat.ChatDTO;
import pl.denys.dto.chat.PrivateChatDTO;
import pl.denys.model.UserEntity;
import pl.denys.model.chat.Chat;
import pl.denys.model.chat.PrivateChat;

import java.util.List;

@Mapper
public interface ChatMapper {
    Chat chatDTOToChat(ChatDTO chatDTO);

    @Mappings({
            @Mapping(source = "partnerId", target = "partner", qualifiedByName = "toUserEntity"),
            @Mapping(source = "creator", target = "creator", qualifiedByName = "toUserEntity")
    })
    PrivateChat chatDTOToPrivateChat(ChatDTO chatDTO);

    @Mappings
            ({
                    @Mapping(source = "privateChat", target = "name", qualifiedByName = "getName"),
                    @Mapping(source = "privateChat", target = "receiverId", qualifiedByName = "getReceiverId"),
                    @Mapping(source = "id", target = "id"),
            })
    PrivateChatDTO privateChatToPrivateChatDTO(PrivateChat privateChat);

    List<PrivateChatDTO> privateChatToPrivateChatDTOList(List<PrivateChat> privateChats);

    @Named("getName")
    default String getName(PrivateChat privateChat) {
        UserEntity userEntity;
        if (privateChat.getCustomerId().equals(privateChat.getCreator().getId())) userEntity = privateChat.getPartner();
        else userEntity = privateChat.getCreator();

        return userEntity.getFirstName() + " " + userEntity.getLastName();
    }

    @Named("getReceiverId")
    default String getReceiverId(PrivateChat privateChat) {
        UserEntity userEntity;
        if (privateChat.getCustomerId().equals(privateChat.getCreator().getId())) userEntity = privateChat.getPartner();
        else userEntity = privateChat.getCreator();

        return userEntity.getId();
    }

    @Named("toUserEntity")
    default UserEntity toUserEntity(String id) {
        var userEntity = new UserEntity();
        userEntity.setId(id);
        return userEntity;
    }
}
