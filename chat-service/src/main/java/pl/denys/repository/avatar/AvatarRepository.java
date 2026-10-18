package pl.denys.repository.avatar;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.denys.model.UserEntity;
import pl.denys.model.avatar.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Avatar findByUser(UserEntity user);
}
