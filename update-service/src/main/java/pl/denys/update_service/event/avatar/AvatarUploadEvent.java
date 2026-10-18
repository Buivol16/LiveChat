package pl.denys.update_service.event.avatar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvatarUploadEvent implements Serializable {
    private String userId;
    private String imageFileName;
}