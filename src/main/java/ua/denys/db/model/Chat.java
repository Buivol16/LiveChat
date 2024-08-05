package ua.denys.db.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Builder
public class Chat {
  @Id @Column @GeneratedValue Long id;

  @Column(name = "name", nullable = false)
  String name;

  @OneToMany(mappedBy = "chat")
  List<Message> messages;

  @JoinColumn(name = "author_id", nullable = false)
  @ManyToOne
  Client author;

  @JoinColumn(name = "recipient_id")
  @ManyToOne
  Client recipient;

  @Column(name = "is_private", nullable = false)
  boolean isPrivate;
}
