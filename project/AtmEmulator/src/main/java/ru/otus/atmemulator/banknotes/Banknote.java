package ru.otus.atmemulator.banknotes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public record Banknote(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        String type,
        int count
) {
    public Banknote() {
        this(null, null, 0);
    }


}