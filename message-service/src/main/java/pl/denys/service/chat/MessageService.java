package pl.denys.service.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.denys.configuration.context.CorrelationIdContextHolder;
import pl.denys.dto.message.MessageDTO;
import pl.denys.dto.message.MessageReadEventDTO;
import pl.denys.event.message.MessageCreatedEvent;
import pl.denys.event.message.MessageReadEvent;
import pl.denys.mapper.MessageMapper;
import pl.denys.repository.MessageRepository;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MessageService {
    private final StreamBridge streamBridge;
    private final MessageRepository repository;
    private final MessageMapper mapper = Mappers.getMapper(MessageMapper.class);

    public void createMessage(MessageDTO messageDTO) {
        messageDTO.setAuthorId(SecurityContextHolder.getContext().getAuthentication().getName());
        streamBridge.send(
                "message-created-out-0",
                new MessageCreatedEvent(messageDTO, CorrelationIdContextHolder.getCorrelationId()));
    }

    public List<MessageDTO> getAllMessages(Long chatId, boolean isPrivate) {
        if (isPrivate) {
            var allPrivateChatMessages = repository.getAllPrivateChatMessages(chatId);
            var dtos = mapper.messagesToMessageDTO(allPrivateChatMessages);
            return dtos;
        } else {
            return Collections.emptyList();
        }
    }

    public void readMessage(List<MessageReadEventDTO> messageIds) {
        log.info("Reading message with size {} with correlation id: {}", messageIds.size(), CorrelationIdContextHolder.getCorrelationId());
        streamBridge.send(
                "message-read-out-0",
                new MessageReadEvent(messageIds, CorrelationIdContextHolder.getCorrelationId()));
    }
}
