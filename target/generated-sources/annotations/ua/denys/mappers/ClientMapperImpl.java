package ua.denys.mappers;

import javax.annotation.processing.Generated;
import ua.denys.db.model.Client;
import ua.denys.model.ClientDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-02T14:21:08+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientDTO clientToClientDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        String specialId = null;
        String name = null;

        specialId = client.getSpecialId();
        name = client.getName();

        ClientDTO clientDTO = new ClientDTO( specialId, name );

        return clientDTO;
    }
}
