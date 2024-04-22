package ru.otus.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.crm.model.Client;
import ru.otus.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Client> getClient(Long id) {
        return clientRepository.findById(id);
    }

    @Transactional
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Transactional
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Client> getAllClients() {
        return (List<Client>) clientRepository.findAll();
    }
}