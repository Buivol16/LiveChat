package pl.denys.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.denys.model.UserEntity;
import pl.denys.model.chat.Chat;
import pl.denys.model.member.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByUser(UserEntity user);

    boolean existsByUserAndChat(UserEntity user, Chat chat);
}
