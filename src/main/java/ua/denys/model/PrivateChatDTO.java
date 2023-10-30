package ua.denys.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class PrivateChatDTO {
    String id;
    ClientDTO creator;
    ClientDTO participant;
    List<MessageDTO> messages;
}
