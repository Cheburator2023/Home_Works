package ru.otus.atmemulator.entity.atm;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.atmemulator.entity.Banknotes;
import ru.otus.atmemulator.entity.Currency;

import java.math.BigDecimal;
import java.util.EnumMap;
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

    @ElementCollection
    @CollectionTable(name = "atm_banknotes", joinColumns = @JoinColumn(name = "atm_id"))
    @MapKeyColumn(name = "banknote")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "count")
    private Map<Banknotes, Integer> banknotes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cash_keeper_id", referencedColumnName = "id")
    private CashKeeper cashKeeper;

    public ATM(Map<Currency, BigDecimal> balance, Map<Banknotes, Integer> banknotes, CashKeeper cashKeeper) {
        this.balance = balance;
        this.banknotes = banknotes;
        this.cashKeeper = cashKeeper;
        updateBalance();
    }

    public void updateBalance() {
        Map<Currency, BigDecimal> newBalance = new EnumMap<>(Currency.class);
        for (Banknotes banknote : Banknotes.values()) {
            Currency currency = banknote.getCurrency();
            BigDecimal nominal = BigDecimal.valueOf(banknote.getNominal());
            int count = banknotes.getOrDefault(banknote, 0);
            newBalance.put(currency, newBalance.getOrDefault(currency, BigDecimal.ZERO).add(nominal.multiply(BigDecimal.valueOf(count))));
        }
        this.balance = newBalance;
    }
}