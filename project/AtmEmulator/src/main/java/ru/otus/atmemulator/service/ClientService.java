package ru.otus.atmemulator.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.atmemulator.entity.clients.Client;
import ru.otus.atmemulator.repository.ClientRepository;


@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public Client findByUsername(String username) {
        return clientRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}