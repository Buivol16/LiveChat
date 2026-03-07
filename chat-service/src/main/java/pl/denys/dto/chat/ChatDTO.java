package pl.denys.dto.chat;

import lombok.Data;
import pl.denys.dto.user.UserDTO;

@Data
public class ChatDTO {
    private String name;
    private UserDTO creator;
}
