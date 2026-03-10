package ua.denys.db.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.denys.db.model.Client;
import ua.denys.db.model.PrivateChat;

public interface PrivateChatRepository extends JpaRepository<PrivateChat, String> {
    Optional<PrivateChat> findByCreatorAndParticipant(Client creator, Client participant);
}
