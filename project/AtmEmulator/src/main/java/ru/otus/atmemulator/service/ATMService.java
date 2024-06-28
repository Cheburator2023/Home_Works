package ru.otus.atmemulator.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.atmemulator.entity.atm.ATM;
import ru.otus.atmemulator.exception.ATMNotFoundException;
import ru.otus.atmemulator.repository.ATMRepository;

@Service
public class ATMService {

    private final ATMRepository atmRepository;

    @Autowired
    public ATMService(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    @Transactional
    public ATM getATM(Long id) {
        return atmRepository.findById(id).orElseThrow(() -> new ATMNotFoundException("ATM not found"));
    }
}