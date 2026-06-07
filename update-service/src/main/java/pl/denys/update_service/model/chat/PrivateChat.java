package pl.denys.update_service.model.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "chats", schema = "private")
@ToString
public class PrivateChat {
    private Long id;
    private String creator;
    private String partnerId;
    private Timestamp createdAt;
    private Boolean deletedForCreator;
    private Boolean deletedForPartner;
}
