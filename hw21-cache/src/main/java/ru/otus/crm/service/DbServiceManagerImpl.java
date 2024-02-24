package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.crm.model.Manager;

import java.util.List;
import java.util.Optional;

public class DbServiceManagerImpl implements DBServiceManager {
    private static final Logger log = LoggerFactory.getLogger(DbServiceManagerImpl.class);

    private final DataTemplate<Manager> dataTemplate;
    private final TransactionRunner transactionRunner;

    private final HwCache<Long, Manager> cache;

    public DbServiceManagerImpl(TransactionRunner transactionRunner, DataTemplate<Manager> dataTemplate, HwCache<Long, Manager> cache) {
        this.transactionRunner = transactionRunner;
        this.dataTemplate = dataTemplate;
        this.cache = cache;
    }

    @Override
    public Manager saveManager(Manager manager) {
        return transactionRunner.doInTransaction(connection -> {
            if (manager.getNo() == null) {
                var managerNo = dataTemplate.insert(connection, manager);
                var createdManager = new Manager(managerNo, manager.getLabel(), manager.getParam1());
                log.info("created manager: {}", createdManager);
                cache.put(createdManager.getNo(), createdManager);
                return createdManager;
            }
            dataTemplate.update(connection, manager);
            log.info("updated manager: {}", manager);
            cache.put(manager.getNo(), manager);
            return manager;
        });
    }

    @Override
    public Optional<Manager> getManager(long no) {
        Optional<Manager> managerOptional = Optional.ofNullable(cache.get(no));
        if (managerOptional.isPresent()) {
            log.info("manager from cache: {}", managerOptional.get());
            return managerOptional;
        }

        managerOptional = transactionRunner.doInTransaction(connection -> dataTemplate.findById(connection, no));
        managerOptional.ifPresent(manager -> cache.put(no, manager));
        log.info("manager from database: {}", managerOptional.orElse(null));
        return managerOptional;
    }

    @Override
    public List<Manager> findAll() {
        List<Manager> managerList = cache.getAll();
        if (!managerList.isEmpty()) {
            log.info("managerList from cache: {}", managerList);
            return managerList;
        }

        managerList = transactionRunner.doInTransaction(dataTemplate::findAll);
        for (Manager manager : managerList) {
            cache.put(manager.getNo(), manager);
        }
        log.info("managerList from database: {}", managerList);
        return managerList;
    }
}
