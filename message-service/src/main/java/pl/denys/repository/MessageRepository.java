package pl.denys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;import org.springframework.stereotype.Repository;
import pl.denys.model.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.privateChatId = ?1 ORDER BY m.id ASC")
    List<Message> getAllPrivateChatMessages(Long chatId);

    @Query("""
    SELECT DISTINCT
    	CASE
    		WHEN m.privateChatId IS NOT NULL THEN m.authorId
    	ELSE
    		null
    	END
    FROM Message m WHERE id IN (?1)
""")
    List<String> getAllGivenUserIds(List<Long> messageIds);
}
