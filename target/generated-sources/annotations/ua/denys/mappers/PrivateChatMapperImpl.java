package ua.denys.mappers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import ua.denys.db.model.Client;
import ua.denys.db.model.Message;
import ua.denys.db.model.PrivateChat;
import ua.denys.model.ClientDTO;
import ua.denys.model.MessageDTO;
import ua.denys.model.PrivateChatDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-02T14:21:07+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
public class PrivateChatMapperImpl implements PrivateChatMapper {

    private final MessageMapper messageMapper = MessageMapper.INSTANCE;

    @Override
    public PrivateChatDTO privateChatToPrivateChatDTO(PrivateChat privateChat) {
        if ( privateChat == null ) {
            return null;
        }

        String id = null;
        ClientDTO creator = null;
        ClientDTO participant = null;
        List<MessageDTO> messages = null;

        id = privateChat.getId();
        creator = clientToClientDTO( privateChat.getCreator() );
        participant = clientToClientDTO( privateChat.getParticipant() );
        messages = messageListToMessageDTOList( privateChat.getMessages() );

        PrivateChatDTO privateChatDTO = new PrivateChatDTO( id, creator, participant, messages );

        return privateChatDTO;
    }

    protected ClientDTO clientToClientDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        String specialId = null;
        String name = null;

        specialId = client.getSpecialId();
        name = client.getName();

        ClientDTO clientDTO = new ClientDTO( specialId, name );

        return clientDTO;
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
