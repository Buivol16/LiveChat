package pl.denys.update_service.repository.message;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.denys.update_service.model.message.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
