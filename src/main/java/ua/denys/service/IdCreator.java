package ua.denys.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class IdCreator {

  public String createId() {
    var idLength = 5;
    return RandomStringUtils.random(idLength, true, true);
  }
}
