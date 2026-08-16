package pl.denys.model.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import pl.denys.model.UserEntity;
import pl.denys.model.chat.Chat;

import java.io.Serializable;

@Entity
@Table(name="members", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
}
