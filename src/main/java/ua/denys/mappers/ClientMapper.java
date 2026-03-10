package ua.denys.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.denys.db.model.Client;
import ua.denys.model.ClientDTO;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDTO clientToClientDTO(Client client);
}
