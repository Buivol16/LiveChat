package pl.denys.model.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.ToString;
import pl.denys.model.UserEntity;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "chats", schema = "private")
@ToString
public class PrivateChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private UserEntity creator;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @ManyToOne
    @JoinColumn(name = "partner_id", nullable = false)
    private UserEntity partner;
    @Column(name = "deleted_for_creator", nullable = false)
    private Boolean deletedForCreator;
    @Column(name = "deleted_for_partner", nullable = false)
    private Boolean deletedForPartner;
    @Transient
    private String customerId;
}