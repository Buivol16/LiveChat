package pl.denys.update_service.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.denys.update_service.model.chat.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
