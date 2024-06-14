package ru.otus.atmemulator.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.atmemulator.entity.accounts.Account;
import ru.otus.atmemulator.entity.accounts.AccountBalanceHistory;
import ru.otus.atmemulator.entity.Banknotes;
import ru.otus.atmemulator.entity.Currency;
import ru.otus.atmemulator.exception.AccountNotFoundException;
import ru.otus.atmemulator.exception.BanknoteNotFoundException;
import ru.otus.atmemulator.exception.InsufficientFundsException;
import ru.otus.atmemulator.repository.AccountRepository;
import ru.otus.atmemulator.repository.AccountBalanceHistoryRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountBalanceHistoryRepository accountBalanceHistoryRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountBalanceHistoryRepository accountBalanceHistoryRepository) {
        this.accountRepository = accountRepository;
        this.accountBalanceHistoryRepository = accountBalanceHistoryRepository;
    }

    @Transactional
    public List<Account> getAccountsByClientId(Long clientId) {
        return accountRepository.findByClientId(clientId);
    }

    @Transactional
    public void deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));

        if (!isAmountValid(amount, account.getCurrency())) {
            throw new RuntimeException("Amount must be a multiple of the banknote denomination");
        }

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        AccountBalanceHistory history = new AccountBalanceHistory(account, amount);
        accountBalanceHistoryRepository.save(history);
    }

    @Transactional
    public void withdraw(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        if (!isAmountValid(amount, account.getCurrency())) {
            throw new BanknoteNotFoundException("Amount must be a multiple of the banknote denomination");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        AccountBalanceHistory history = new AccountBalanceHistory(account, amount.negate());
        accountBalanceHistoryRepository.save(history);
    }

    private boolean isAmountValid(BigDecimal amount, Currency currency) {
        for (Banknotes banknote : Banknotes.values()) {
            if (banknote.getCurrency() == currency && amount.remainder(BigDecimal.valueOf(banknote.getNominal())).compareTo(BigDecimal.ZERO) == 0) {
                return true;
            }
        }
        return false;
    }

    public List<Banknotes> getAllBanknotes() {
        return Stream.of(Banknotes.values()).collect(Collectors.toList());
    }
}