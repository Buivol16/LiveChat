package ua.denys.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.denys.db.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
