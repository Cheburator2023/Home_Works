package ru.otus.atmemulator.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.atmemulator.entity.clients.Client;
import ru.otus.atmemulator.repository.ClientRepository;

@Service
public class ClientDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(ClientDetailsService.class);

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientDetailsService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Attempting to load user by username: {}", username);
        Client client = clientRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found: {}", username);
                    return new UsernameNotFoundException("User not found");
                });

        logger.debug("User found: {}", client.getUsername());

        return User.builder()
                .username(client.getUsername())
                .password(client.getPassword())
                .roles("USER")
                .build();
    }

//    @Transactional
//    public void createAdminUser() {
//        if (clientRepository.findByUsername("admin").isEmpty()) {
//            Client admin = new Client();
//            admin.setUsername("admin");
//            admin.setPassword(passwordEncoder.encode("password"));
//            clientRepository.save(admin);
//        }
//    }
}