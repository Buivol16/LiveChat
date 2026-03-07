package pl.denys.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.denys.db.facade.ClientFacade;
import pl.denys.model.ClientDTO;

@RestController
@RequiredArgsConstructor
public class ClientController {

  private final ClientFacade clientFacade;

  @PostMapping("/create-client")
  public ClientDTO createClient(String name) {
    return clientFacade.createClient(name);
  }

}
