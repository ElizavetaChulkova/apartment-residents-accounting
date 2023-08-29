package ru.chulkova.residents.accounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.chulkova.residents.accounting.model.Apartment;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    @Query(nativeQuery = true, value = """
            SELECT a.id, a.address, a.owner_id 
            FROM apartment a WHERE a.owner_id = :ownerId
                        """)
    List<Apartment> getAllByOwner(@Param("ownerId") Long ownerId);

    @Override
    Optional<Apartment> findById(Long aLong);

    Optional<Apartment> findApartmentByAddress(String address);
}