package pl.denys.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.denys.model.chat.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    boolean existsByCreatorAndId(String creator, Long id);
}
