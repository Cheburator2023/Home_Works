package ru.otus.atmemulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.atmemulator.entity.atm.ATM;

public interface ATMRepository extends JpaRepository<ATM, Long> {
}