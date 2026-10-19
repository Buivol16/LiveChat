package pl.denys.update_service.service.avatar;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.denys.update_service.event.avatar.AvatarChatUploadEvent;
import pl.denys.update_service.event.avatar.AvatarUploadEvent;
import pl.denys.update_service.model.UserEntity;
import pl.denys.update_service.model.avatar.Avatar;
import pl.denys.update_service.model.chat.Chat;
import pl.denys.update_service.repository.avatar.AvatarRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvatarService {
    private final AvatarRepository avatarRepository;

    @Transactional
    public void handleAvatarUpload(AvatarUploadEvent event){
        log.info("Handling avatar upload via service layer: {}", event);
        var avatar = new Avatar();
        log.debug("Setting userId");
        var user = new UserEntity();
        user.setId(event.getUserId());
        avatar.setUser(user);
        log.debug("Setting avatar image fileName");
        avatar.setFileName(event.getImageFileName());
        avatar.setUsing(true);
        log.debug("Saving new avatar to database");
        try{
            saveAndSetUnusedStatusForAvatarsByUserId(avatar);
            log.debug("Avatar saved");
        }catch (Exception e){
            log.error("Error while saving avatar: {}", e);
            e.printStackTrace();
        }
    }

    @Transactional
    public void handleAvatarChatUpload(AvatarChatUploadEvent event){
        log.info("Handling avatar chat upload via service layer: {}", event);
        var avatar = new Avatar();
        log.debug("Setting chatId");
        var chat = new Chat();
        chat.setId(event.getChatId());
        avatar.setChat(chat);
        log.debug("Setting avatar image fileName");
        avatar.setFileName(event.getImageFileName());
        avatar.setUsing(true);
        log.debug("Saving new avatar to database");
        try{
            saveAndSetUnusedStatusForAvatarsByChatId(avatar);
            log.debug("Avatar saved");
        }catch (Exception e){
            log.error("Error while saving avatar: {}", e);
            e.printStackTrace();
        }
    }

    public void saveAndSetUnusedStatusForAvatarsByUserId(Avatar avatar){
        avatarRepository.setUnusedStatusForAvatarsByUserId(avatar.getUser().getId());
        avatarRepository.save(avatar);
    }

    public void saveAndSetUnusedStatusForAvatarsByChatId(Avatar avatar){
        avatarRepository.setUnusedStatusForAvatarsByChatId(avatar.getChat().getId());
        avatarRepository.save(avatar);
    }
}
