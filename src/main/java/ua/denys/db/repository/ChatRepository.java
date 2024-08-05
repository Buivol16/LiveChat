package ua.denys.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.denys.db.model.Chat;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
  boolean existsByName(String name);

  @Query("SELECT c FROM Chat c WHERE c.recipient.specialId LIKE ?1")
  Optional<Chat> findByRecipientSpecialId(String recipientSpecialId);
}
