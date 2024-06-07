package ru.otus.atmemulator.entity.atm;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.atmemulator.entity.Currency;

import java.math.BigDecimal;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "atm")
public class ATM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "atm_balance", joinColumns = @JoinColumn(name = "atm_id"))
    @MapKeyColumn(name = "currency")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "balance")
    private Map<Currency, BigDecimal> balance;

    public ATM(Map<Currency, BigDecimal> balance) {
        this.balance = balance;
    }
}