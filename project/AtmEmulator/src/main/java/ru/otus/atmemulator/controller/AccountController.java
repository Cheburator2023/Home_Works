package ru.otus.atmemulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.atmemulator.entity.accounts.Account;
import ru.otus.atmemulator.entity.atm.ATM;
import ru.otus.atmemulator.entity.clients.Client;
import ru.otus.atmemulator.service.AccountService;
import ru.otus.atmemulator.service.ATMService;
import ru.otus.atmemulator.service.ClientService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    private final ClientService clientService;
    private final AccountService accountService;
    private final ATMService atmService;

    @Autowired
    public AccountController(ClientService clientService, AccountService accountService, ATMService atmService) {
        this.clientService = clientService;
        this.accountService = accountService;
        this.atmService = atmService;
    }

    @GetMapping
    public String getAccounts(Model model, Authentication authentication) {
        Client client = clientService.findByUsername(authentication.getName());
        List<Account> accounts = accountService.getAccountsByClientId(client.getId());
        model.addAttribute("accounts", accounts);
        ATM atm = atmService.getATM(1L); // хард код т.к. в примере только один банкомат
        model.addAttribute("atm", atm);
        model.addAttribute("allBanknotes", accountService.getAllBanknotes());
        return "accounts";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam Long accountId, @RequestParam BigDecimal amount) {
        accountService.deposit(accountId, amount);
        return "redirect:/accounts";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam Long accountId, @RequestParam BigDecimal amount) {
        accountService.withdraw(accountId, amount);
        return "redirect:/accounts";
    }
}