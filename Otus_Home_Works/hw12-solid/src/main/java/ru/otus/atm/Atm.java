package ru.otus.atm;

import ru.otus.balance.Balance;
import ru.otus.banknotes.Banknotes;

import java.util.List;

public interface Atm {
    void getBanknoutes(List<Banknotes> banknoutes);

    List<Banknotes> giveBanknotes(int giveOutCash);

    void getAccountBalance();
}
