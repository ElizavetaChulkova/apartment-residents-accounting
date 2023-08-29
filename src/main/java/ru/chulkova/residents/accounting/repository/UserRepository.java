package ru.chulkova.residents.accounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chulkova.residents.accounting.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);
}