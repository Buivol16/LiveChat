package pl.denys.notification_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/notification")
public class NotificationController {
    private final StreamBridge streamBridge;

    @PostMapping("/confirm")
    public void confirm(@RequestBody String notificationUuid) {
        log.info("Received MessageCreatedEvent with notificationUuid {}", notificationUuid);
        streamBridge.send("notification_accepted", notificationUuid);
    }
}
