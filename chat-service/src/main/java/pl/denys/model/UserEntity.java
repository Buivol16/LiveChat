package pl.denys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "user_entity", schema = "keycloak")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {
    @Id
    private String id;
    @Column
    private String firstName;
    @Column
    private String lastName;
}
