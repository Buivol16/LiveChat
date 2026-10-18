package pl.denys.update_service.service.avatar;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.denys.update_service.event.avatar.AvatarUploadEvent;
import pl.denys.update_service.model.UserEntity;
import pl.denys.update_service.model.avatar.Avatar;
import pl.denys.update_service.repository.avatar.AvatarRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvatarService {
    private final AvatarRepository avatarRepository;

    public void handleAvatarUpload(AvatarUploadEvent event){
        log.info("Handling avatar upload via service layer: {}", event);
        var avatar = new Avatar();
        log.debug("Setting userId");
        var user = new UserEntity();
        user.setId(event.getUserId());
        avatar.setUser(user);
        log.debug("Setting avatar image fileName");
        avatar.setFileName(event.getImageFileName());
        log.debug("Saving new avatar to database");
        try{
            save(avatar);
            log.debug("Avatar saved");
        }catch (Exception e){
            log.error("Error while saving avatar: {}", e);
            e.printStackTrace();
        }
    }

    public void save(Avatar avatar){
        avatarRepository.save(avatar);
    }
}
