package pl.denys.db.model;

import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "message")
@Data
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Message {
  @Id @GeneratedValue @Column Long id;

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  Client author;

  @ManyToOne
  @JoinColumn(name = "chat_id")
  Chat chat;

  @Column(length = 200)
  String text;

  @Column(name = "published_at")
  @CreatedDate
  LocalDateTime publishedAt;
}
