package pl.denys.notification_service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
  private final SimpMessagingTemplate simpMessagingTemplate;

  @GetMapping
  public void test() {
    simpMessagingTemplate.convertAndSend("/topic/notification", "Hello world from test controller!");
  }
}
