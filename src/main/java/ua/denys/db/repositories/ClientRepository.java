package ua.denys.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.denys.db.model.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findBySpecialId(String specialId);
    boolean existsBySpecialId(String specialId);
}
