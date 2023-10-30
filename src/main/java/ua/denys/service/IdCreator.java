package ua.denys.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class IdCreator {

  public String createId() {
    var idBuilder = new StringBuilder();
    var random = new Random();
    for (int i = 0; i < 5; i++) {
      if (random.nextBoolean()) idBuilder.append(random.nextInt(9));
      else idBuilder.append(RandomStringUtils.random(1, true, false));
    }
    return idBuilder.toString();
  }
}
