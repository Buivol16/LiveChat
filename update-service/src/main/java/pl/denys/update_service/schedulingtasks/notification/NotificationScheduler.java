package pl.denys.update_service.schedulingtasks.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.denys.update_service.service.notification.NotificationService;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationScheduler {
    private final NotificationService notificationService;

    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.MINUTES)
    public void checkForUnacceptedNotificationsAndSendIt(){
        log.info("Checking for unaccepted notifications");
        //todo finish scheduler about checking unaccepted notifications
        notificationService.checkAndSendUnacceptedNotification();
    }
}
