package pl.denys.notification_service.event.userjoin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.denys.notification_service.dto.UserEntity;
import pl.denys.notification_service.event.chat.Chat;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
    private Long id;
    private UserEntity user;
    private Chat chat;
}
