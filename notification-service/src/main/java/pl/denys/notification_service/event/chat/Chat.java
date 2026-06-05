package pl.denys.notification_service.event.chat;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Chat {
    private Long id;
    private String name;
    private String creator;
}