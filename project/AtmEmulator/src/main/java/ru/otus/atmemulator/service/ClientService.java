package ru.otus.atmemulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.atmemulator.entity.atm.ATM;
import ru.otus.atmemulator.entity.accounts.Account;
import ru.otus.atmemulator.entity.atm.CashKeeper;
import ru.otus.atmemulator.entity.Banknotes;
import ru.otus.atmemulator.entity.Currency;
import ru.otus.atmemulator.entity.clients.Client;
import ru.otus.atmemulator.repository.ATMRepository;
import ru.otus.atmemulator.repository.AccountRepository;
import ru.otus.atmemulator.repository.ClientRepository;

import jakarta.annotation.PostConstruct;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ATMRepository atmRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientRepository clientRepository, AccountRepository accountRepository, ATMRepository atmRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.atmRepository = atmRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (clientRepository.count() == 0) {
            Client client = new Client();
            client.setUsername("user");
            client.setPassword(passwordEncoder.encode("password"));
            clientRepository.save(client);

            Account rubAccount = new Account("RUB123", BigDecimal.valueOf(1000), Currency.RUB, client);
            accountRepository.save(rubAccount);

            Account dollarAccount = new Account("USD123", BigDecimal.valueOf(100), Currency.DOLLAR, client);
            accountRepository.save(dollarAccount);

            Account eurAccount = new Account("EUR123", BigDecimal.valueOf(100), Currency.EURO, client);
            accountRepository.save(eurAccount);
        }

        if (atmRepository.count() == 0) {
            Map<Currency, Integer> initialBanknoteCount = new EnumMap<>(Currency.class);
            initialBanknoteCount.put(Currency.RUB, 1000);
            initialBanknoteCount.put(Currency.DOLLAR, 500);
            initialBanknoteCount.put(Currency.EURO, 300);
            CashKeeper cashKeeper = new CashKeeper(initialBanknoteCount);

            Map<Currency, BigDecimal> initialBalance = new EnumMap<>(Currency.class);
            Map<Banknotes, Integer> initialBanknotes = new EnumMap<>(Banknotes.class);
            Random random = new Random();

            for (Banknotes banknote : Banknotes.values()) {
                int count = random.nextInt(100);
                initialBanknotes.put(banknote, count);
            }

            ATM atm = new ATM(initialBalance, initialBanknotes, cashKeeper);
            atmRepository.save(atm);
        }
    }

    public Client findByUsername(String username) {
        return clientRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Account> getAccountsByClientId(Long clientId) {
        return accountRepository.findByClientId(clientId);
    }

    public ATM getATM() {
        return atmRepository.findById(1L).orElseThrow(() -> new RuntimeException("ATM not found"));
    }

    public void deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        ATM atm = atmRepository.findById(1L).orElseThrow(() -> new RuntimeException("ATM not found"));

        if (!isAmountValid(amount, account.getCurrency())) {
            throw new RuntimeException("Amount must be a multiple of the banknote denomination");
        }

        account.setBalance(account.getBalance().add(amount));
        atm.getBalance().put(account.getCurrency(), atm.getBalance().get(account.getCurrency()).add(amount));

        accountRepository.save(account);
        atmRepository.save(atm);
    }

    public void withdraw(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        ATM atm = atmRepository.findById(1L).orElseThrow(() -> new RuntimeException("ATM not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        if (atm.getBalance().get(account.getCurrency()).compareTo(amount) < 0) {
            throw new RuntimeException("ATM has insufficient funds");
        }

        if (!isAmountValid(amount, account.getCurrency())) {
            throw new RuntimeException("Amount must be a multiple of the banknote denomination");
        }

        account.setBalance(account.getBalance().subtract(amount));
        atm.getBalance().put(account.getCurrency(), atm.getBalance().get(account.getCurrency()).subtract(amount));

        accountRepository.save(account);
        atmRepository.save(atm);
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