package pl.denys.update_service.event.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.denys.update_service.dto.message.MessageReadEventDTO;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageReadEvent implements Serializable {
    private List<MessageReadEventDTO> messageIds;
    private String correlationId;
}
