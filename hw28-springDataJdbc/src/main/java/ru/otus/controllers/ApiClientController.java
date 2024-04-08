package ru.otus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.crm.model.Client;
import ru.otus.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ApiClientController {

    private final ClientService clientService;

    public ApiClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        return clientService.getClient(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        if (!clientService.getClient(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        client.setId(id);
        return ResponseEntity.ok(clientService.saveClient(client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        if (!clientService.getClient(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }
}