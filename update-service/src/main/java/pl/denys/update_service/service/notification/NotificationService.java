package pl.denys.update_service.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.denys.update_service.event.message.MessageCreatedEvent;
import pl.denys.update_service.exceptions.BusinessLogicException;
import pl.denys.update_service.model.notification.Notification;
import pl.denys.update_service.model.notification.NotificationStatus;
import pl.denys.update_service.model.notification.NotificationType;
import pl.denys.update_service.repository.notification.NotificationRepository;
import pl.denys.update_service.service.message.MessageService;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static pl.denys.update_service.model.notification.NotificationStatus.ACCEPTED;
import static pl.denys.update_service.model.notification.NotificationStatus.SENT_TO_QUEUE;
import static pl.denys.update_service.model.notification.NotificationStatus.UNACCEPTED;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final MessageService messageService;
    private final StreamBridge streamBridge;

    @Value("${notification.checker.minutes:30}")
    private long minutesToMinus;

    public void checkAndSendUnacceptedNotification() {
        Timestamp nowMinusMinutes = Timestamp.from(Instant.now(Clock.systemUTC()).minus(minutesToMinus, ChronoUnit.MINUTES));
        var notifications = notificationRepository.findByStatusAndRecentlySentAtBefore(UNACCEPTED, nowMinusMinutes);
        for (var notification : notifications) {
            switch (notification.getType()) {
                case MESSAGE_CREATED -> sendMessageNotification(notification);
            }
        }
        notificationRepository.saveAll(notifications);
    }

    @Transactional
    public void acceptNotification(String notificationUuid) {
        try {
            log.info("Accepting notification {}", notificationUuid);
            notificationRepository.updateStatusByUuid(notificationUuid,ACCEPTED);
            log.info("Notification {} accepted", notificationUuid);
        }catch (Throwable e) {
            log.error("Error accepting notification {} {}", notificationUuid, e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Notification saveAsNotification(Long entityId, NotificationType notificationType) {
        var timestampNow = Timestamp.from(Clock.systemUTC().instant());
        var uuid = UUID.randomUUID().toString();
        var notification = new Notification();
        notification.setUuid(uuid);
        notification.setType(notificationType);
        notification.setCreatedAt(timestampNow);
        notification.setRecentlySentAt(timestampNow);
        notification.setEntityId(entityId);
        notification.setStatus(NotificationStatus.SENT_TO_QUEUE);
        return notificationRepository.save(notification);
    }

    private void sendMessageNotification(Notification notification) {
        log.info("Sending notification with id {} with recentlySentAt {}", notification.getUuid(), notification.getRecentlySentAt());
        var messageId = notification.getEntityId();
        try {
            var createdMessage = messageService.findById(messageId).orElseThrow(() -> new BusinessLogicException("Message not found"));
            var event = new MessageCreatedEvent(createdMessage, UUID.randomUUID().toString(), notification.getUuid());
            log.info("Message has been successfully created {} for correlationId {}", createdMessage.toString(), event.getCorrelationId());
            streamBridge.send("created_chat_notification", event);
            log.info("Created message notification has been sent");
            notification.setStatus(SENT_TO_QUEUE);
            notification.setRecentlySentAt(Timestamp.from(Instant.now(Clock.systemUTC())));
        } catch (Throwable t) {
            log.error("Some exception occurred while trying send message as notification, {}", t.getMessage());
            t.printStackTrace();
        }
    }
}
