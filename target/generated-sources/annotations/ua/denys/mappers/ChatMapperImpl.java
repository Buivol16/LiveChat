package ua.denys.mappers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import ua.denys.db.model.Chat;
import ua.denys.db.model.Message;
import ua.denys.db.model.PrivateChat;
import ua.denys.model.ChatDTO;
import ua.denys.model.MessageDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-02T14:21:08+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
public class ChatMapperImpl implements ChatMapper {

    private final MessageMapper messageMapper = MessageMapper.INSTANCE;

    @Override
    public ChatDTO chatToChatDTO(Chat chat) {
        if ( chat == null ) {
            return null;
        }

        ChatDTO.ChatDTOBuilder chatDTO = ChatDTO.builder();

        chatDTO.messageDTOs( messageListToMessageDTOList( chat.getMessages() ) );
        chatDTO.id( chat.getId() );
        chatDTO.name( chat.getName() );

        return chatDTO.build();
    }

    @Override
    public ChatDTO privateChatToChatDTO(PrivateChat privateChat) {
        if ( privateChat == null ) {
            return null;
        }

        ChatDTO.ChatDTOBuilder chatDTO = ChatDTO.builder();

        chatDTO.messageDTOs( messageListToMessageDTOList( privateChat.getMessages() ) );
        chatDTO.id( privateChat.getId() );

        return chatDTO.build();
    }

    protected List<MessageDTO> messageListToMessageDTOList(List<Message> list) {
        if ( list == null ) {
            return null;
        }

        List<MessageDTO> list1 = new ArrayList<MessageDTO>( list.size() );
        for ( Message message : list ) {
            list1.add( messageMapper.messageToMessageDTO( message ) );
        }

        return list1;
    }
}
