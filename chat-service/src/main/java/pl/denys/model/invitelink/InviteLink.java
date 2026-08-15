package pl.denys.model.invitelink;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.denys.model.chat.Chat;

@Entity
@Table(name = "invitelinks", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InviteLink {
    @Id
    private String uuid;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
}
