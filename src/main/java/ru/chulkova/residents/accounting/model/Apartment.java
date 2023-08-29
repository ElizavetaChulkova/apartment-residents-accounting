package ru.chulkova.residents.accounting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", nullable = false, referencedColumnName = "id")
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apartment")
    private List<User> residents;
}