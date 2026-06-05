package pl.denys.controller.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.denys.configuration.context.CorrelationIdContextHolder;
import pl.denys.dto.message.MessageDTO;
import pl.denys.service.chat.MessageService;

@Slf4j
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity createMessage(@RequestBody MessageDTO messageDTO) {
        log.info("Creating message with correlation id: {}", CorrelationIdContextHolder.getCorrelationId());
        messageService.createMessage(messageDTO);
        return ResponseEntity.ok().build();
    }
}
