package pl.denys.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
}
