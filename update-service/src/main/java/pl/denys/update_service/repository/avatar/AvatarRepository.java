package pl.denys.update_service.repository.avatar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.denys.update_service.model.avatar.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    @Query("UPDATE Avatar a SET a.isUsing = false WHERE a.user.id = ?1")
    @Modifying
    void setUnusedStatusForAvatarsByUserId(String id);

    @Query("UPDATE Avatar a SET a.isUsing = false WHERE a.chat.id = ?1")
    @Modifying
    void setUnusedStatusForAvatarsByChatId(Long id);

}
