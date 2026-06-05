package pl.denys.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.denys.model.chat.Chat;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatCreatedEvent implements Serializable {
    private Chat chat;
    private String correlationId;
}
