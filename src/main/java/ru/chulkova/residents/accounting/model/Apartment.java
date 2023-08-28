package ru.chulkova.residents.accounting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "apartment")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Apartment extends AbstractBaseEntity {

    @Column(name = "address", nullable = false)
    private String address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;
}