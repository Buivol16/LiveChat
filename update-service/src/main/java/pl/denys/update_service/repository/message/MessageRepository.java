package pl.denys.update_service.repository.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.denys.update_service.model.message.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Modifying
    @Query("UPDATE Message m set m.isRead = true WHERE m.id IN (?1)")
    void readMessages(List<Long> messageId);
}
