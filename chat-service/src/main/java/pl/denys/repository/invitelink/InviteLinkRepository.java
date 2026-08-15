package pl.denys.repository.invitelink;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.denys.model.chat.Chat;
import pl.denys.model.invitelink.InviteLink;

import java.util.Optional;

public interface InviteLinkRepository extends JpaRepository<InviteLink, String> {
    Optional<InviteLink> findByChat(Chat chat);
}
