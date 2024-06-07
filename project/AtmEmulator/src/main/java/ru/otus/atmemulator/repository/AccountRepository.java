package ru.otus.atmemulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.atmemulator.entity.Currency;
import ru.otus.atmemulator.entity.accounts.Account;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByClientId(Long clientId);
    List<Account> findByClientIdAndCurrency(Long clientId, Currency currency);
}