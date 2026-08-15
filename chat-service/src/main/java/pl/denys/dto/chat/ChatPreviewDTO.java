package pl.denys.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatPreviewDTO {
    private String iconLink;
    private String name;
    private String description;
}
