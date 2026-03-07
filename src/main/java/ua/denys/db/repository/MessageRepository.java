package pl.denys.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.denys.db.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
