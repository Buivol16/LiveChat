package pl.denys.update_service.event.avatar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvatarChatUploadEvent {
    private Long chatId;
    private String imageFileName;
    private Timestamp uploadedAt;
}
