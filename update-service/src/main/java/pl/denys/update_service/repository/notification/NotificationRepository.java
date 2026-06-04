package pl.denys.update_service.repository.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.denys.update_service.model.notification.Notification;
import pl.denys.update_service.model.notification.NotificationStatus;

import java.sql.Timestamp;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findByStatusAndRecentlySentAtBefore(NotificationStatus status, Timestamp time);
    @Modifying
    @Query("UPDATE Notification n SET status = ?2 WHERE n.uuid = ?1")
    void updateStatusByUuid(String uuid, NotificationStatus status);
}
