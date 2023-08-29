package ru.chulkova.residents.accounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.chulkova.residents.accounting.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value =
            """
                    UPDATE users SET apartment_id = :apartmentId WHERE id = :userId
                    """)
    void update(@Param("userId") Long userId, @Param("apartmentId") Long apartmentId);
}