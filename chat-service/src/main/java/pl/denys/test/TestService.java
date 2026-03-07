package pl.denys.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.denys.configuration.context.CorrelationIdContextHolder;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {
  private final StreamBridge streamBridge;

  public ResponseEntity recordTemperature(TemperatureDTO temperatureDTO) {
    if (temperatureDTO.getTime() == null) {
      temperatureDTO.setTime(LocalDateTime.now(Clock.systemUTC()));
    }
    var corId = CorrelationIdContextHolder.getCorrelationId();
    log.info("Recording temperature CorrelationId: {}", corId);
    var event = new TemperatureInputEvent(corId, temperatureDTO, "Denys Khmara Home");
    try {
      var ok =
          streamBridge.send(
              "temperatures-out-0",
              MessageBuilder.withPayload(event).setHeader(KafkaHeaders.KEY, "123".getBytes(StandardCharsets.UTF_8)).build());
      return ok ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }
}
