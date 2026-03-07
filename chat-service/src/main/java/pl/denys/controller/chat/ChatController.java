package pl.denys.controller.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.denys.dto.chat.ChatDTO;
import pl.denys.service.chat.ChatService;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private ChatService chatService;

    @PostMapping
    public ResponseEntity createChat(ChatDTO chatDTO){
        chatService.createChat(chatDTO);
        return ResponseEntity.ok().build();
    }
}
