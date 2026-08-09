package pl.denys.update_service.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageReadEventDTO {
    private String authorId;
    private Long messageId;
}