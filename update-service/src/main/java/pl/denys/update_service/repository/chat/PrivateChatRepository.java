package pl.denys.update_service.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.denys.update_service.model.chat.PrivateChat;

public interface PrivateChatRepository extends JpaRepository<PrivateChat, Long> {
}
