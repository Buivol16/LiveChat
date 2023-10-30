package ua.denys.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ChatCreationInputDTO{
    String chatName;
    ClientDTO clientDTO;
}
