package pl.denys.update_service.event.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationAcceptedEvent {
    private String notificationUuid;
}
