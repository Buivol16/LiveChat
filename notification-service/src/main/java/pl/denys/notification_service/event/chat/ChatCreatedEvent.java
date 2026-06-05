package pl.denys.notification_service.event.chat;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.denys.notification_service.event.message.Message;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatCreatedEvent implements Serializable {
    private Chat chat;
    private Message message;
    private String notificationUuid;
    private String correlationId;
}

