package ru.otus.cashkeeper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.otus.banknotes.Banknotes;

import java.util.Map;

@Getter
@Setter
@ToString
public class CashKeeper {

    Map<Banknotes, Integer> innerCash;

    public CashKeeper(Map<Banknotes, Integer> innerCash) {
        this.innerCash = innerCash;
    }
}
