package pl.denys.controller.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/join/{uuid}")
    public ResponseEntity enterChat(@PathVariable(name = "uuid") String code){
        log.info("Entering chat with correlation id: {}", CorrelationIdContextHolder.getCorrelationId());
        chatService.enterChat(code);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/create-invite")
    public ResponseEntity createChatInviteCode(@RequestParam Long chatId){
        log.info("Creating chat invite link for chat {} with correlation id: {}", chatId, CorrelationIdContextHolder.getCorrelationId());
        return ResponseEntity.ok(chatService.createInviteCodeOrReturnExisting(chatId));
    }

    @GetMapping("/chat-preview")
    public ResponseEntity getChatPreview(@RequestParam String code){
        return ResponseEntity.ok(chatService.getChatPreview(code));
    }

    @GetMapping("/private")
    public ResponseEntity getAllPrivateChats(){
        log.info("Retrieving all private chats with correlation id: {}", CorrelationIdContextHolder.getCorrelationId());
        return ResponseEntity.ok(chatService.getAllPrivateByCreatorIdOrPartnerId());
    }

    @GetMapping("/public")
    public ResponseEntity getAllPublicChats(){
        log.info("Retrieving all public chats with correlation id: {}", CorrelationIdContextHolder.getCorrelationId());
        return ResponseEntity.ok(chatService.getAllPublicByUserId());
    }

    @GetMapping("/members")
    public ResponseEntity getAllMembersOfChat(Long chatId){
        log.info("Retreiving all members of chat {} with correlation id: {}", chatId, CorrelationIdContextHolder.getCorrelationId());
        return ResponseEntity.ok(chatService.getMembersByChatId(chatId));
    }

    @DeleteMapping("/remove-member")
    public ResponseEntity removeMemberOfChat(@RequestParam Long memId){
        log.info("Deleting chat member with id {} and correlationId {}", memId, CorrelationIdContextHolder.getCorrelationId());
        chatService.removeMember(memId);
        return ResponseEntity.noContent().build();
    }
}
