package pl.denys.controller.avatar;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.denys.service.avatar.AvatarService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/avatar")
//todo rename it to UserAvatarController or something similar to this meaning
public class AvatarController {
    private final AvatarService avatarService;

    @PostMapping("/upload")
    public ResponseEntity uploadUserAvatar(@RequestBody MultipartFile file){
        return avatarService.uploadUserAvatar(file);
    }

    @GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity getUserAvatarImage(){
        return avatarService.getUserAvatarImage();
    }

    @GetMapping(value = "/file/{filename}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity getUserAvatarImageByFilename(@PathVariable String filename){
        return avatarService.getAvatarImageByFilename(filename);
    }

    @GetMapping(value = "/{chatId}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity getPublicChatAvatarImage(@PathVariable Long chatId){
        return avatarService.getChatAvatarImage(chatId);
    }

    @PostMapping("/upload/{chatId}")
    public ResponseEntity uploadAvatarToPublicChat(@RequestBody MultipartFile file, @PathVariable Long chatId){
        return avatarService.uploadAvatarToChat(file, chatId);
    }
}
