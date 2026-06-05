package pl.denys.service.chat;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import pl.denys.configuration.context.CorrelationIdContextHolder;
import pl.denys.dto.chat.ChatDTO;
import pl.denys.event.ChatCreatedEvent;
import pl.denys.mapper.ChatMapper;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final StreamBridge streamBridge;

    private final ChatMapper mapper = Mappers.getMapper(ChatMapper.class);

    public void createChat(ChatDTO chatDTO) {
        var chat = mapper.chatDTOToChat(chatDTO);
        streamBridge.send("chat-created-out-0", new ChatCreatedEvent(chat, CorrelationIdContextHolder.getCorrelationId()));
    }
}
