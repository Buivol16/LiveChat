package pl.denys.update_service.event.message;


import pl.denys.update_service.dto.message.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageCreatedEvent implements Serializable {
    private MessageDTO message;
    private String correlationId;
    private String notificationUuid;
}