package pl.denys.repository.userentity;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.denys.model.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {
}
