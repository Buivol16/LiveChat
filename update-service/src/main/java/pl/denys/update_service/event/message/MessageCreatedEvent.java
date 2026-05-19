package pl.denys.update_service.event.message;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.denys.update_service.model.message.Message;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageCreatedEvent implements Serializable {
    private Message message;
    private String correlationId;
}