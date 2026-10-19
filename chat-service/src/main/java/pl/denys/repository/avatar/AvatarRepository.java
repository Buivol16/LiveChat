package pl.denys.repository.avatar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.denys.model.UserEntity;
import pl.denys.model.avatar.Avatar;
import pl.denys.model.chat.Chat;

import java.util.List;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Avatar findByUser(UserEntity user);

    Avatar findByChat(Chat chat);

    @Query(value = """
            SELECT
            	a.*
            FROM
            	public.avatars a
            LEFT JOIN private.chats c ON
            c.creator_id = a.user_id OR c.partner_id = a.user_id\s
            WHERE a.user_id != ?1 AND c.id IN (?2)
            """, nativeQuery = true)
    List<Avatar> findImageFilenameByRequesterUserIdAndChatId(String userId, List<Long> chatIds);
}
