package ua.denys.mappers;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ua.denys.db.model.Client;
import ua.denys.db.model.Message;
import ua.denys.db.repositories.ChatRepository;
import ua.denys.db.repositories.ClientRepository;
import ua.denys.db.repositories.PrivateChatRepository;
import ua.denys.exceptions.EntityNotFoundException;
import ua.denys.model.ClientDTO;
import ua.denys.model.MessageDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-02T16:37:56+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class MessageMapperImpl extends MessageMapper {

    @Override
    public MessageDTO messageToMessageDTO(Message message) {
        if ( message == null ) {
            return null;
        }

        ClientDTO author = null;
        String text = null;
        LocalDateTime publishedAt = null;

        author = clientToClientDTO( message.getAuthor() );
        text = message.getText();
        publishedAt = message.getPublishedAt();

        String chatId = null;

        MessageDTO messageDTO = new MessageDTO( author, chatId, text, publishedAt );

        return messageDTO;
    }

    @Override
    public Message messageDTOToMessage(MessageDTO messageDTO, ChatRepository chatRepository, ClientRepository clientRepository) throws EntityNotFoundException {
        if ( messageDTO == null ) {
            return null;
        }

        Message.MessageBuilder message = Message.builder();

        message.text( messageDTO.getText() );
        message.publishedAt( messageDTO.getPublishedAt() );

        message.chat( chatRepository
            .findById(messageDTO.getChatId())
            .orElseThrow(() -> new EntityNotFoundException("Chat is not found by id.")) );
        message.author( clientRepository
            .findBySpecialId(messageDTO.getAuthor().getSpecialId())
            .orElseThrow(() -> new EntityNotFoundException("Client is not found by id.")) );

        return message.build();
    }

    @Override
    public Message messageDTOToMessage(MessageDTO messageDTO, PrivateChatRepository privateChatRepository, ClientRepository clientRepository) throws EntityNotFoundException {
        if ( messageDTO == null ) {
            return null;
        }

        Message.MessageBuilder message = Message.builder();

        message.text( messageDTO.getText() );
        message.publishedAt( messageDTO.getPublishedAt() );

        message.privateChat( privateChatRepository
            .findById(messageDTO.getChatId())
            .orElseThrow(() -> new EntityNotFoundException("Chat is not found by id.")) );
        message.author( clientRepository
            .findBySpecialId(messageDTO.getAuthor().getSpecialId())
            .orElseThrow(() -> new EntityNotFoundException("Client is not found by id.")) );

        return message.build();
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
}
