package pl.denys.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
  private final TestService testService;

  @PostMapping
  public ResponseEntity testPost(@RequestBody TemperatureDTO temperatureDTO) {
    return testService.recordTemperature(temperatureDTO);
  }

  @GetMapping
  public String testGet() {
    return "Hello world!";
  }
}
