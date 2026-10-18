package pl.denys.update_service.repository.avatar;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.denys.update_service.model.avatar.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

}
