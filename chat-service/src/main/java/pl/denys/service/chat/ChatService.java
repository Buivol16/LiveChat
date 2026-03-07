package pl.denys.service.chat;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import pl.denys.dto.chat.ChatDTO;
import pl.denys.mapper.ChatMapper;
import pl.denys.repository.chat.ChatRepository;

@Service
@RequiredArgsConstructor
public class ChatService {
//    private final ChatRepository repository;

    private final ChatMapper mapper = Mappers.getMapper(ChatMapper.class);

    public void createChat(ChatDTO chatDTO) {
        var chat = mapper.chatDTOToChat(chatDTO);
//        repository.save(chat);
    }
}
