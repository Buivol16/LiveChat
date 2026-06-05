package pl.denys.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.denys.db.model.Client;
import pl.denys.model.ClientDTO;

@Mapper
public interface ClientMapper {

  ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

  ClientDTO clientToClientDTO(Client client);
}
