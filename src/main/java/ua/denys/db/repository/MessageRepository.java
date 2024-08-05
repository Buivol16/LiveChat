package ua.denys.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.denys.db.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
