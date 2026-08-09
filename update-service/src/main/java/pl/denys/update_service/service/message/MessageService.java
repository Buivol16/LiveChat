package pl.denys.update_service.service.message;

import pl.denys.update_service.dto.message.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.denys.update_service.dto.message.MessageReadEventDTO;
import pl.denys.update_service.mapper.message.MessageMapper;
import pl.denys.update_service.model.chat.PrivateChat;
import pl.denys.update_service.model.message.Message;
import pl.denys.update_service.repository.message.MessageRepository;

import java.sql.Timestamp;
import java.time.Clock;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {
    private final MessageRepository repository;

    private final MessageMapper mapper = Mappers.getMapper(MessageMapper.class);

//    public Message createMessage(Message message) throws RuntimeException {
//        try {
//            log.info("Start creating message by {} user", message.getAuthorId());
//            var chat = new Chat();
//            chat.setId(message.getChatId());
//            message.setChat(chat);
//            message.setCreatedAt(Timestamp.from(Clock.systemUTC().instant()));
//            var newMessage = messageRepository.save(message);
//            return newMessage;
//        } catch (RuntimeException e) {
//            log.error("An exception happened while trying to create a new message {}", e.getMessage());
//            e.printStackTrace();
//            throw e;
//        }
//    }

    public MessageDTO createMessageForPrivateChat(MessageDTO messageDTO) throws RuntimeException {
        try {
            log.info("Start creating message by {} user", messageDTO.getAuthorId());
            var chat = new PrivateChat();
            chat.setId(messageDTO.getChatId());
            messageDTO.setCreatedAt(Timestamp.from(Clock.systemUTC().instant()));
            var message = mapper.messageDTOToMessage(messageDTO);
            message.setPrivateChat(chat);
            log.info("Saving message by {} user", messageDTO.getAuthorId());
            var saved = repository.save(message);
            messageDTO.setId(saved.getId());
            log.info("Message created by {} user", messageDTO.getAuthorId());
            return messageDTO;
        } catch (RuntimeException e) {
            log.error("An exception happened while trying to create a new messageDTO {}", e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Optional<Message> findById(Long messageId) {
        return repository.findById(messageId);
    }

    public void readMessages(List<MessageReadEventDTO> messageReadEventDTOS) {
        var messageIds = messageReadEventDTOS.stream().map(MessageReadEventDTO::getMessageId).toList();
        repository.readMessages(messageIds);
    }
}
