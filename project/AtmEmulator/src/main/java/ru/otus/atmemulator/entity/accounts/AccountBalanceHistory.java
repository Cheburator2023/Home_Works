package ru.otus.atmemulator.entity.accounts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "account_balance_history")
public class AccountBalanceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "change_amount", nullable = false)
    private BigDecimal changeAmount;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public AccountBalanceHistory(Account account, BigDecimal changeAmount) {
        this.account = account;
        this.changeAmount = changeAmount;
        this.timestamp = LocalDateTime.now();
    }
}