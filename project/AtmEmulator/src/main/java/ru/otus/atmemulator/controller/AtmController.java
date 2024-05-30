package ru.otus.atmemulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.atmemulator.banknotes.Banknote;
import ru.otus.atmemulator.service.AtmService;

import java.util.List;

@Controller
public class AtmController {
    private final AtmService atmService;

    @Autowired
    public AtmController(AtmService atmService) {
        this.atmService = atmService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("balance", atmService.getAccountBalance());
        return "index";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam List<Banknote> banknotes, Model model) {
        atmService.getBanknotes(banknotes);
        model.addAttribute("balance", atmService.getAccountBalance());
        return "index";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam int amount, Model model) {
        List<Banknote> banknotes = atmService.giveBanknotes(amount);
        model.addAttribute("banknotes", banknotes);
        model.addAttribute("balance", atmService.getAccountBalance());
        return "index";
    }
}
