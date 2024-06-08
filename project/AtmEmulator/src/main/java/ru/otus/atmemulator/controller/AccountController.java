package ru.otus.atmemulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.atmemulator.entity.accounts.Account;
import ru.otus.atmemulator.entity.atm.ATM;
import ru.otus.atmemulator.entity.clients.Client;
import ru.otus.atmemulator.service.ClientService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    private final ClientService clientService;

    @Autowired
    public AccountController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String getAccounts(Model model, Authentication authentication) {
        Client client = clientService.findByUsername(authentication.getName());
        List<Account> accounts = clientService.getAccountsByClientId(client.getId());
        model.addAttribute("accounts", accounts);
        ATM atm = clientService.getATM();
        model.addAttribute("atm", atm);
        model.addAttribute("allBanknotes", clientService.getAllBanknotes());
        return "accounts";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam Long accountId, @RequestParam BigDecimal amount) {
        clientService.deposit(accountId, amount);
        return "redirect:/accounts";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam Long accountId, @RequestParam BigDecimal amount) {
        clientService.withdraw(accountId, amount);
        return "redirect:/accounts";
    }
}