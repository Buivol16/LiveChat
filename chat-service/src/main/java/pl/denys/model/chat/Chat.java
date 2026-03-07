package pl.denys.model.chat;

import lombok.Data;
import pl.denys.model.user.User;

@Data
public class Chat {
    private Long id;
    private String name;
    private User creator;
}
