package pl.denys.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.denys.model.chat.Chat;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByCreator(String creatorId);

    boolean existsByCreatorAndId(String creator, Long id);
}
