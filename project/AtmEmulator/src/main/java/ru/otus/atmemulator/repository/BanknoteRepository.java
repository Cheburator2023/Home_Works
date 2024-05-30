package ru.otus.atmemulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.atmemulator.banknotes.Banknote;

import java.util.Optional;

public interface BanknoteRepository extends JpaRepository<Banknote, Long> {
    Banknote findByType(String type);
}