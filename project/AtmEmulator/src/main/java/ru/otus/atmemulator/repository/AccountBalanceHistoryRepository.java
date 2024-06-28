package ru.otus.atmemulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.atmemulator.entity.accounts.AccountBalanceHistory;

public interface AccountBalanceHistoryRepository extends JpaRepository<AccountBalanceHistory, Long> {
}