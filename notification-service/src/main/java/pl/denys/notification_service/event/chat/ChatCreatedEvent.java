package pl.denys.notification_service.event.chat;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatCreatedEvent implements Serializable {
    private Chat chat;
    private String correlationId;
}

