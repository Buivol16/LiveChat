package pl.denys.update_service.model.message;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.ToString;
import pl.denys.update_service.model.chat.Chat;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "messages", schema = "private")
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //TODO Replace transient field with created different dto class and map all fields from this class to an entity
    @Transient
    private Long chatId;
    @JoinColumn(name = "chat_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Chat chat;
    @Column(name = "author_id")
    private String authorId;
    @Column(name = "receiver_id")
    private String receiverId;
    @Column(name = "encrypted_message", length = 500)
    private String encryptedMessage;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
    @Column(name = "deleted_for_all")
    private boolean deletedForAll;
    @Column(name = "deleted_for_author_only")
    private boolean deletedForAuthorOnly;
}
