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
@UniqueConstraint(name = "one_owner", columnNames = {"id", "owner_id"}))
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Apartment extends AbstractBaseEntity {

    @Column(name = "address", nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "owner_id", nullable = false, referencedColumnName = "id")
    private User owner;

//    @OneToMany
//    private List<User> residents;
}