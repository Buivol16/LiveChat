package pl.denys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Timestamp;

@Entity
@Table(name="messages", schema = "private")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    private Long id;
    private Long publicChatId;
    @Column()
    private Long privateChatId;
    @Column(nullable = false)
    private String authorId;
    @Column(nullable = false)
    private String receiverId;
    @Column(nullable = false, length = 500)
    private String encryptedMessage;
    @Column(nullable = false)
    private Timestamp createdAt;
    @Column()
    private Timestamp modifiedAt;
    @Column()
    private Boolean deletedForAll;
    @Column()
    private Boolean deletedForAuthorOnly;
    @Column
    private Boolean isRead;

    @Transient
    private String authenticationContextUuid = SecurityContextHolder.getContext().getAuthentication().getName();
}
