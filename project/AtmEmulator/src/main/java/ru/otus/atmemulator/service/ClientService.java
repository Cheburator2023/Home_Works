package ru.otus.atmemulator.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.atmemulator.entity.clients.Client;
import ru.otus.atmemulator.repository.ClientRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Client findByUsername(String username) {
        return clientRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}