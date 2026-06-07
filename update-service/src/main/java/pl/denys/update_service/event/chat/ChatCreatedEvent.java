package pl.denys.update_service.event.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.denys.update_service.model.chat.Chat;
import pl.denys.update_service.model.chat.PrivateChat;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatCreatedEvent implements Serializable {
    private Chat publicChat;
    private PrivateChat privateChat;
    private boolean isPublicChat;
    private String correlationId;
}

