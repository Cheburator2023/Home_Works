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
@Table(name = "cash_keeper")
public class CashKeeper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "cash_keeper_balance", joinColumns = @JoinColumn(name = "cash_keeper_id"))
    @MapKeyColumn(name = "currency")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "count")
    private Map<Currency, Integer> banknoteCount;

    public CashKeeper(Map<Currency, Integer> banknoteCount) {
        this.banknoteCount = banknoteCount;
    }

    public BigDecimal getTotalAmount(Currency currency, BigDecimal banknoteValue) {
        return banknoteValue.multiply(BigDecimal.valueOf(banknoteCount.getOrDefault(currency, 0)));
    }

    public int getBanknoteCount(Currency currency) {
        return banknoteCount.getOrDefault(currency, 0);
    }
}