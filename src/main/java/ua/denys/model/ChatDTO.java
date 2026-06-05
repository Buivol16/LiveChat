package pl.denys.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@Builder
public class ChatDTO {
    String id;
    String name;
    List<MessageDTO> messageDTOs;
    boolean isPrivate;
}
