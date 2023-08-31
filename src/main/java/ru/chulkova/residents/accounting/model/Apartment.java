package ru.chulkova.residents.accounting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "apartment", uniqueConstraints =
@UniqueConstraint(name = "one_owner", columnNames = {"id", "owner_id"}),
        indexes = @Index(name = "address_idx", columnList = "address"))
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Apartment extends AbstractBaseEntity {

    @Column(name = "address", nullable = false, unique = true)
    private String address;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "owner_id", nullable = false, referencedColumnName = "id")
    private User owner;
}