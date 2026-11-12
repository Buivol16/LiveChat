package pl.denys.repository.avatar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.denys.model.UserEntity;
import pl.denys.model.avatar.Avatar;
import pl.denys.model.chat.Chat;

import java.util.List;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Avatar findByUserAndIsUsingTrue(UserEntity user);

    Avatar findByChat(Chat chat);

    @Query(value = """
            SELECT
            	a.*
            FROM
            	public.avatars a
            LEFT JOIN private.chats c ON
            c.creator_id = a.user_id OR c.partner_id = a.user_id
            WHERE a.user_id != ?1 AND c.id IN (?2)
            """, nativeQuery = true)
    List<Avatar> findAllPrivateChatAvatarsByUserIdAndPrivateChatId(String userId, List<Long> chatIds);

    @Query(value = """
            SELECT a.* FROM avatars a
            JOIN public.members m ON m.chat_id = a.chat_id
            WHERE m.user_id = ?1
            AND is_using = TRUE
            ORDER BY a.chat_id ASC;
            """, nativeQuery = true)
    List<Avatar> findAllPublicChatAvatarsWhereUserIdIsMember(String userId);
}
