package ru.otus.atmemulator.entity.accounts;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import ru.otus.atmemulator.entity.Banknotes;

@Getter
@Setter
@Entity
public class DollarTableStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long atmId;

    @Column(name = "DOLLARS50")
    private Banknotes DOLLARS50;
    @Column(name = "DOLLARS100")
    private Banknotes DOLLARS100;
}