package ua.denys.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "privatechats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivateChat {
  @Id @Column String id;

  @JoinColumn(name = "creator_person_id")
  @ManyToOne
  Client creator;

  @JoinColumn(name = "participant_person_id")
  @ManyToOne
  Client participant;

  @OneToMany(mappedBy = "privateChat")
  List<Message> messages;
}
