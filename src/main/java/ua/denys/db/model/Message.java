package ua.denys.db.model;

import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "messages")
@Data
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
  @Id @GeneratedValue @Column Long id;

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  Client author;

  @ManyToOne
  @JoinColumn(name = "chat_id")
  Chat chat;

  @ManyToOne
  @JoinColumn(name = "private_chat_id")
  PrivateChat privateChat;

  @Column(length = 200)
  String text;

  @Column(name = "published_at")
  LocalDateTime publishedAt;
}
