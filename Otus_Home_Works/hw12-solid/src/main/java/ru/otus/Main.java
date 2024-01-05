package ru.otus;

import ru.otus.atm.Atm;
import ru.otus.atm.AtmImpl;
import ru.otus.banknotes.Banknotes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.otus.balance.Balance.balance;

public class Main {
    public static void main(String[] args) {
        List<Banknotes> banknotesRecivedClient = new ArrayList<>();
        banknotesRecivedClient.add(Banknotes.RUBLES100);
        banknotesRecivedClient.add(Banknotes.RUBLES500);
        banknotesRecivedClient.add(Banknotes.RUBLES1000);
        banknotesRecivedClient.add(Banknotes.RUBLES5000);

        Map<Banknotes, Integer> banknotesInAtm = new HashMap<>();
        banknotesInAtm.put(Banknotes.RUBLES100, 100);
        banknotesInAtm.put(Banknotes.RUBLES500, 20);
        banknotesInAtm.put(Banknotes.RUBLES1000, 10);
        banknotesInAtm.put(Banknotes.RUBLES5000, 10);

        int giveOutCash = 5800;

        Atm atm = new AtmImpl(banknotesInAtm);
        atm.getBanknoutes(banknotesRecivedClient);
        atm.giveBanknotes(giveOutCash);
        atm.getAccountBalance();
        balance +=1000000;
        atm.getAccountBalance();
        atm.giveBanknotes(balance);
    }
}