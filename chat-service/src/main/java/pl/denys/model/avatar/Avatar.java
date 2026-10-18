package pl.denys.model.avatar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.denys.model.UserEntity;

@Entity
@Table(name = "avatars", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long avatarId;
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
