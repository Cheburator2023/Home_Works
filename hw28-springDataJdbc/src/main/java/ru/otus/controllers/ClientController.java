package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.crm.model.Client;
import ru.otus.service.ClientService;

import java.util.Optional;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String listClients(Model model) {
        try {
            model.addAttribute("clients", clientService.getAllClients());
            return "clients/list";
        } catch (Exception e) {
            logger.error("Error listing clients", e);
            throw e;
        }
    }

    @GetMapping("/{id:[\\d]+}")
    public String getClient(@PathVariable Long id, Model model) {
        try {
            Optional<Client> clientOpt = clientService.getClient(id);
            if (clientOpt.isPresent()) {
                Client client = clientOpt.get();
                model.addAttribute("client", client);
            } else {
                model.addAttribute("client", null);
            }
            return "clients/view";
        } catch (Exception e) {
            logger.error("Error getting client with ID: " + id, e);
            throw e;
        }
    }

    @GetMapping("/new")
    public String newClient(Model model) {
        try {
            model.addAttribute("client", new Client());
            return "clients/edit";
        } catch (Exception e) {
            logger.error("Error creating new client form", e);
            throw e;
        }
    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute Client client) {
        try {
            clientService.saveClient(client);
            return "redirect:/clients";
        } catch (Exception e) {
            logger.error("Error saving client", e);
            throw e;
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteClient(id);
            return "redirect:/clients";
        } catch (Exception e) {
            logger.error("Error deleting client with ID: " + id, e);
            throw e;
        }
    }
}