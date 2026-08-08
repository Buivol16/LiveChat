package pl.denys.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.denys.model.UserEntity;
import pl.denys.model.chat.PrivateChat;

import java.util.List;

public interface PrivateChatRepository extends JpaRepository<PrivateChat, Long> {
    @Query("SELECT p FROM PrivateChat p WHERE p.creator = ?1 or p.partner = ?1")
    List<PrivateChat> findAllByCreatorIdOrPartnerId(UserEntity entity);
}
