package ru.otus.atmemulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.atmemulator.service.ClientDetailsService;

@SpringBootApplication
public class AtmEmulatorApplication implements CommandLineRunner {

    private final ClientDetailsService clientDetailsService;

    public AtmEmulatorApplication(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AtmEmulatorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        clientDetailsService.createAdminUser();
    }
}
