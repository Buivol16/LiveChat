package pl.denys.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.denys.configuration.context.CorrelationIdContextHolder;
import pl.denys.dto.message.MessageDTO;
import pl.denys.dto.message.MessageReadEventDTO;
import pl.denys.service.chat.MessageService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService service;

    @PostMapping
    public ResponseEntity createMessage(@RequestBody MessageDTO messageDTO) {
        log.info("Creating message with correlation id: {}", CorrelationIdContextHolder.getCorrelationId());
        service.createMessage(messageDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getAllMessages(@RequestParam Long chatId, @RequestParam boolean isPrivate) {
        log.info("Getting all messages with correlation id: {}", CorrelationIdContextHolder.getCorrelationId());
        var messages = service.getAllMessages(chatId, isPrivate);
        return ResponseEntity.ok(messages);
    }

    @PatchMapping("/read")
    public ResponseEntity readMessage(@RequestBody List<MessageReadEventDTO> messageId){
        service.readMessage(messageId);
        return ResponseEntity.ok().build();
    }
}
