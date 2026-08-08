package pl.denys.notification_service.event.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageReadEvent implements Serializable {
    private List<Long> messageIds;
    private List<String> receiverUuid;
    private String correlationId;
}
