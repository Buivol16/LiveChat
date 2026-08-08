package pl.denys.dto.chat;

import lombok.Data;
import pl.denys.dto.user.UserDTO;

@Data
public class ChatDTO {
    private Long id;
    private String imgSrc;
    private String name;
    private String[] members;
    private String creator;
    private String partnerId;
    private Boolean isPublic = true;
}
