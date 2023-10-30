package ua.denys.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class MessageDTO {
    ClientDTO author;
    String chatId;
    String text;
    LocalDateTime publishedAt;
}
