package pl.denys.update_service.model.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "notifications", schema = "private")
@ToString
public class Notification {
    @Id
    @Column(name = "uuid", unique = true, nullable = false, length = 36)
    private String uuid;
    @Column(name = "entity_id")
    private Long entityId;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "recently_sent_at")
    private Timestamp recentlySentAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NotificationStatus status;
}