package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> dataTemplate;
    private final TransactionRunner transactionRunner;
    private final HwCache<Long, Client> cache;

    public DbServiceClientImpl(TransactionRunner transactionRunner, DataTemplate<Client> dataTemplate, HwCache<Long, Client> cache) {
        this.transactionRunner = transactionRunner;
        this.dataTemplate = dataTemplate;
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionRunner.doInTransaction(connection -> {
            if (client.getId() == null) {
                var clientId = dataTemplate.insert(connection, client);
                var createdClient = new Client(clientId, client.getName());
                log.info("created client: {}", createdClient);
                cache.put(clientId, createdClient);
                return createdClient;
            }
            dataTemplate.update(connection, client);
            log.info("updated client: {}", client);
            cache.put(client.getId(), client);
            return client;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        Optional<Client> clientOptional = Optional.ofNullable(cache.get(id));
        if (clientOptional.isPresent()) {
            log.info("client from cache: {}", clientOptional.get());
            return clientOptional;
        }

        clientOptional = transactionRunner.doInTransaction(connection -> dataTemplate.findById(connection, id));
        clientOptional.ifPresent(client -> cache.put(id, client)); // Добавляем клиента в кэш
        log.info("client from database: {}", clientOptional.orElse(null));
        return clientOptional;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clientList = cache.getAll();
        if (!clientList.isEmpty()) {
            log.info("clientList from cache: {}", clientList);
            return clientList;
        }

        clientList = transactionRunner.doInTransaction(dataTemplate::findAll);
        for (Client client : clientList) {
            cache.put(client.getId(), client); // Добавляем каждого клиента в кэш
        }
        log.info("clientList from database: {}", clientList);
        return clientList;
    }
}