package pl.denys.controller.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.denys.configuration.context.CorrelationIdContextHolder;
import pl.denys.dto.chat.ChatDTO;
import pl.denys.service.chat.ChatService;

@Slf4j
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity createChat(@RequestBody ChatDTO chatDTO){
        log.info("Creating chat with correlation id: {}", CorrelationIdContextHolder.getCorrelationId());
        chatService.createChat(chatDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/private")
    public ResponseEntity getAllPrivateChats(){
        log.info("Retrieving all private chats with correlation id: {}", CorrelationIdContextHolder.getCorrelationId());
        return ResponseEntity.ok(chatService.getAllPrivateByCreatorIdOrPartnerId());
    }

    @GetMapping("/public")
    public ResponseEntity getAllPublicChats(){
        log.info("Retrieving all public chats with correlation id: {}", CorrelationIdContextHolder.getCorrelationId());
        return ResponseEntity.ok(chatService.getAllPublicByCreatorId());
    }
}
