package pl.denys.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import pl.denys.configuration.context.CorrelationIdContextHolder;
import pl.denys.dto.message.MessageDTO;
import pl.denys.event.message.MessageCreatedEvent;

@Service
@RequiredArgsConstructor
public class MessageService {
  private final StreamBridge streamBridge;

  public void createMessage(MessageDTO messageDTO) {
    streamBridge.send(
        "message-created-out-0",
        new MessageCreatedEvent(messageDTO, CorrelationIdContextHolder.getCorrelationId()));
  }
}
