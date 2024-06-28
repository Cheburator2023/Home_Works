package ru.otus.atmemulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.atmemulator.entity.atm.CashKeeper;

public interface CashKeeperRepository extends JpaRepository<CashKeeper, Long> {
}