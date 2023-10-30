package ua.denys.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.denys.db.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, String> {
    boolean existsByName(String name);
}
