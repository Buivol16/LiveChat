package pl.denys.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.denys.model.UserEntity;
import pl.denys.model.chat.Chat;
import pl.denys.model.member.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByUser(UserEntity user);

    boolean existsByUserAndChat(UserEntity user, Chat chat);

    List<Member> findAllByChatId(Long chatId);

    @Query("SELECT m.chat.id FROM Member m WHERE m.id = ?1")
    Long findChatIdById(Long memId);
}
