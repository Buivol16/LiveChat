package pl.denys.model.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "chats", schema = "public")
@ToString
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;
    @Column(name = "creator_id", nullable = false)
    private String creator;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
}
