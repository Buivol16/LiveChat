package pl.denys.update_service.consumer.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.denys.update_service.event.notification.NotificationAcceptedEvent;
import pl.denys.update_service.service.notification.NotificationService;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationAcceptedConsumer {
    private final NotificationService notificationService;

    @Bean(name = "notification-accepted")
    public Consumer<NotificationAcceptedEvent> notificationAccepted(){
        return val -> {
            notificationService.acceptNotification(val.getNotificationUuid());
        };
    }

}
