package pl.denys.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class TestController {
  private final TestService testService;

  @PostMapping("/test")
  @ResponseBody
  public ResponseEntity testPost(@RequestBody TemperatureDTO temperatureDTO) {
    return testService.recordTemperature(temperatureDTO);
  }
}
