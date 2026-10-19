package pl.denys.update_service.event.avatar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvatarChatUploadEvent {
    private Long chatId;
    private String imageFileName;
}
