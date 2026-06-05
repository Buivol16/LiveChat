package pl.denys.db.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.denys.db.model.Client;
import pl.denys.db.repository.ClientRepository;
import pl.denys.exceptions.EmptyStringException;
import pl.denys.exceptions.EntityNotFoundException;
import pl.denys.mapper.ClientMapper;
import pl.denys.model.ClientDTO;
import pl.denys.service.IdCreator;

@Component
@RequiredArgsConstructor
public class ClientFacade {
  private final ClientRepository repository;
  private final IdCreator idCreator;

  private static final ClientMapper mapper = ClientMapper.INSTANCE;

  public ClientDTO findById(Long id) throws EntityNotFoundException {
    var client =
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Client with this id is not found."));
    return mapper.clientToClientDTO(client);
  }

  public Client findBySpecialId(String specialId) throws EntityNotFoundException {
    return repository
        .findBySpecialId(specialId)
        .orElseThrow(
            () -> new EntityNotFoundException("Client with this special id is not found."));
  }

  public ClientDTO createClient(String name) throws EmptyStringException {
    if (name.isBlank()) throw new EmptyStringException("The name for client is blank.");
    var specialId = idCreator.createId();
    Client client;
    if (repository.existsBySpecialId(specialId)) return createClient(name);
    client = Client.builder().name(name).specialId(specialId).build();
    repository.save(client);
    return mapper.clientToClientDTO(client);
  }
}
