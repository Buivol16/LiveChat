package ua.denys.db.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.denys.db.model.Client;
import ua.denys.db.repositories.ClientRepository;
import ua.denys.exceptions.EmptyStringException;
import ua.denys.exceptions.EntityNotFoundException;
import ua.denys.mappers.ClientMapper;
import ua.denys.model.ClientDTO;
import ua.denys.service.IdCreator;

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

  public ClientDTO createClient(String name) throws EmptyStringException{
    if (name.isBlank()) throw new EmptyStringException("The name for client is blank.");
    var specialId = idCreator.createId();
    Client client;
    if (repository.existsBySpecialId(specialId)) return createClient(name);
    client = Client.builder().name(name).specialId(specialId).build();
    repository.save(client);
    return mapper.clientToClientDTO(client);
  }
}
