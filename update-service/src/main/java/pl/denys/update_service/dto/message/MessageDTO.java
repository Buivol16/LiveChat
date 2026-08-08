package pl.denys.update_service.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Long id;
    private Long chatId;
    private String authorId;
    private String receiverId;
    private String encryptedMessage;
    private Boolean isPrivateChat;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Boolean deletedForAll;
    private Boolean deletedForAuthorOnly;
}

