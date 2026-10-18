package pl.denys.service.avatar;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.denys.event.AvatarUploadEvent;
import pl.denys.model.UserEntity;
import pl.denys.repository.avatar.AvatarRepository;
import pl.denys.util.AuthenticationContextHolderHelper;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
//todo think up how to reduce tension for this microservice because of this new feature
public class AvatarService {
    private final StreamBridge streamBridge;
    private final AvatarRepository repository;

    public ResponseEntity uploadAvatar(MultipartFile file) {
        log.info("Uploading avatar");
        var imageFileName = UUID.randomUUID() + ".jpg";
        var outputFile = new File(imageFileName);
        compressImageFile(file, outputFile);
        var userId = AuthenticationContextHolderHelper.getAuthentication().getName();
        streamBridge.send("avatar-upload-out-0", new AvatarUploadEvent(userId, imageFileName));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity getAvatar() {
        var userId = AuthenticationContextHolderHelper.getAuthentication().getName();
        var user = new UserEntity(userId, null, null);
        log.info("Getting avatar by userId {}", userId);
        var avatar = repository.findByUser(user);
        log.info("Avatar has been found successfully for userId {}", avatar);
        var imageFile = new File("./" + avatar.getFileName());

        if (!imageFile.exists()) return ResponseEntity.notFound().build();

        byte[] byteArray = new byte[(int) imageFile.length()];
        try (var imageOutputStream = new FileImageOutputStream(imageFile)) {
            imageOutputStream.read(byteArray);
            return ResponseEntity.ok(byteArray);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static void compressImageFile(MultipartFile file, File outputFile) {
        try (var outputStream = ImageIO.createImageOutputStream(outputFile)) {

            var bufferedImage = ImageIO.read(file.getInputStream());

            bufferedImage = resizeImage(bufferedImage);

            var writerIterator = ImageIO.getImageWritersByFormatName("jpg");
            var imageWriter = writerIterator.next();


            imageWriter.setOutput(outputStream);

            var imageParams = imageWriter.getDefaultWriteParam();
            imageParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageParams.setCompressionQuality(0.5f);

            imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageParams);

            imageWriter.dispose();
            log.info("Image created and compressed");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage resizeImage(BufferedImage bufferedImage) {
        Image resultingImage = bufferedImage.getScaledInstance(256, 256, Image.SCALE_SMOOTH);
        var outputImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
}
