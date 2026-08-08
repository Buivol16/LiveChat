package pl.denys.service.chat;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.denys.configuration.context.CorrelationIdContextHolder;
import pl.denys.dto.chat.ChatDTO;
import pl.denys.dto.chat.PrivateChatDTO;
import pl.denys.event.ChatCreatedEvent;
import pl.denys.mapper.ChatMapper;
import pl.denys.model.UserEntity;
import pl.denys.model.chat.Chat;
import pl.denys.model.chat.PrivateChat;
import pl.denys.repository.chat.ChatRepository;
import pl.denys.repository.chat.PrivateChatRepository;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final StreamBridge streamBridge;
    private final PrivateChatRepository privateChatRepository;
    private final ChatRepository chatRepository;

    private final ChatMapper mapper = Mappers.getMapper(ChatMapper.class);

    public void createChat(ChatDTO chatDTO) {
        Chat chat = null;
        PrivateChat privateChat = null;
        var creatorId = SecurityContextHolder.getContext().getAuthentication().getName();
        var isPublic = chatDTO.getIsPublic();
        chatDTO.setCreator(creatorId);
        chatDTO.setName(chatDTO.getPartnerId());
        if (isPublic) {
            chat = createPublicChat(chatDTO);
        } else {
            privateChat = createPrivateChat(chatDTO);
        }

        streamBridge.send("chat-created-out-0", new ChatCreatedEvent(chat, privateChat, isPublic, CorrelationIdContextHolder.getCorrelationId()));
    }

    public PrivateChat createPrivateChat(ChatDTO chatDTO) {
        var chat = mapper.chatDTOToPrivateChat(chatDTO);
        chat.setCreatedAt(Timestamp.from(Instant.now(Clock.systemUTC())));
        return chat;
    }

    private Chat createPublicChat(ChatDTO chatDTO) {
        var chat = mapper.chatDTOToChat(chatDTO);
        chat.setCreatedAt(Timestamp.from(Instant.now(Clock.systemUTC())));
        return chat;
    }

    public List<PrivateChatDTO> getAllPrivateByCreatorIdOrPartnerId() {
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var privateChats = privateChatRepository.findAllByCreatorIdOrPartnerId(new UserEntity(userId, null, null));
        privateChats.forEach(privateChat -> privateChat.setCustomerId(userId));
        return mapper.privateChatToPrivateChatDTOList(privateChats);
    }

    public List<Chat> getAllPublicByCreatorId() {
        var creatorId = SecurityContextHolder.getContext().getAuthentication().getName();
        var publicChats = chatRepository.findAllByCreator(creatorId);
        return publicChats;
    }
}
