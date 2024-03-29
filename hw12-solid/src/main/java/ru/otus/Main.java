package ru.otus;

import ru.otus.atm.AtmImpl;
import ru.otus.banknotes.Banknotes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        int balance = 0;
        List<Banknotes> banknotesRecivedClient = new ArrayList<>();
        banknotesRecivedClient.add(Banknotes.RUBLES100);
        banknotesRecivedClient.add(Banknotes.RUBLES500);
        banknotesRecivedClient.add(Banknotes.RUBLES1000);
        banknotesRecivedClient.add(Banknotes.RUBLES5000);

        Map<Banknotes, Integer> banknotesInAtm = new HashMap<>();
        banknotesInAtm.put(Banknotes.RUBLES100, 1000);
        banknotesInAtm.put(Banknotes.RUBLES500, 1000);
        banknotesInAtm.put(Banknotes.RUBLES1000, 500);
        banknotesInAtm.put(Banknotes.RUBLES5000, 300);

        int giveOutCash = 5800;

        AtmImpl atm = new AtmImpl(banknotesInAtm, balance);
        atm.getSumCashInAtm();
        atm.getBanknoutes(banknotesRecivedClient);
        atm.giveBanknotes(giveOutCash);
        atm.getAccountBalance();
        atm.setBalance(1023400);
        atm.getSumCashInAtm();
        System.out.printf("Остаток на счёте: %d\n", atm.getAccountBalance());
        atm.giveBanknotes(atm.getAccountBalance());
        atm.getAccountBalance();
        atm.getSumCashInAtm();
    }
}