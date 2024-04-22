package ru.otus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.repository.AddressRepository;
import ru.otus.repository.ClientRepository;
import ru.otus.repository.PhoneRepository;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;

    @Autowired
    public DataInitializer(ClientRepository clientRepository, AddressRepository addressRepository, PhoneRepository phoneRepository) {
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
        this.phoneRepository = phoneRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (clientRepository.count() == 0) {
            Address address = new Address("Moscow");

            List<Phone> phones = Arrays.asList(new Phone("346789"));

            Client testClient = new Client("Клиент");
            log.info("Client={}", testClient);

            testClient = clientRepository.save(testClient);

            address.setClientId(testClient.getId());
            address = addressRepository.save(address);
            testClient.setAddress(address);
            log.info("Address={}", testClient.getAddress());

            Client finalTestClient = testClient;
            phones.forEach(phone -> {
                phone.setClientId(finalTestClient.getId());
                phoneRepository.save(phone);
            });
            testClient.setPhones(phones);
            log.info("Phones={}", testClient.getPhones());

            log.info("Client={}", finalTestClient);
            log.info("Address={}", finalTestClient.getAddress());
            log.info("Phones={}", finalTestClient.getPhones());
        } else {
            log.info("База данных не пуста, инициализация пропущена.");
        }
    }
}