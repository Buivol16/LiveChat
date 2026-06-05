package pl.denys.event.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.denys.dto.message.MessageDTO;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageCreatedEvent implements Serializable {
    private MessageDTO message;
    private String correlationId;
}
