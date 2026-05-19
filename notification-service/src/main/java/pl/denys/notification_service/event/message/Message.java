package pl.denys.notification_service.event.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Message {
    private Long id;
    private Long chatId;
    private String authorId;
    private String receiverId;
    private String encryptedMessage;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private boolean deletedForAll;
    private boolean deletedForAuthorOnly;
}
