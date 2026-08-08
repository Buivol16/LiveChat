package pl.denys.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateChatDTO {
    private Long id;
    private String name;
    private String imgSrc;
    private String receiverId;
    private Boolean isOnline = false;
    private Boolean isRead = false;
}
