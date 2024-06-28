package ru.otus.atmemulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.atmemulator.entity.clients.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUsername(String username);
}