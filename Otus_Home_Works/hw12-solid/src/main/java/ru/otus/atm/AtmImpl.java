package ru.otus.atm;

import ru.otus.banknotes.Banknotes;
import ru.otus.exception.NoCashException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.otus.balance.Balance.balance;

public class AtmImpl implements Atm {

    private final Map<Banknotes, Integer> innerCash;

    public AtmImpl(Map<Banknotes, Integer> innerCash) {
        this.innerCash = innerCash;
    }

    @Override
    public void getBanknoutes(List<Banknotes> banknotes) {
        int recivedcash = 0;
        for (Banknotes banknote : banknotes) {
            if (banknote.equals(Banknotes.RUBLES100)) {
                innerCash.put(banknote, (innerCash.get(banknote) + 1));
                recivedcash += 100;
            } else if (banknote.equals(Banknotes.RUBLES500)) {
                innerCash.put(banknote, (innerCash.get(banknote) + 1));
                recivedcash += 500;
            } else if (banknote.equals(Banknotes.RUBLES1000)) {
                innerCash.put(banknote, (innerCash.get(banknote) + 1));
                recivedcash += 1000;
            } else if (banknote.equals(Banknotes.RUBLES5000)) {
                innerCash.put(banknote, (innerCash.get(banknote) + 1));
                recivedcash += 5000;
            } else {
                System.out.println("This is not money");
            }
        }
        System.out.printf("Получены купюры: %s\n", banknotes);
        System.out.printf("Зачислено на счет: %d\n", balance += recivedcash);

    }

    @Override
    public List<Banknotes> giveBanknotes(int giveOutCash) {
        List<Banknotes> banknotesList = new ArrayList<>();
        try {
            if (balance >= giveOutCash) {
                int countOf5000 = giveOutCash / 5000;
                for (int i = 0; i < countOf5000; i++) {
                    if (innerCash.get(Banknotes.RUBLES5000) >= countOf5000) {
                        banknotesList.add(Banknotes.RUBLES5000);
                        innerCash.put(Banknotes.RUBLES5000, (innerCash.get(Banknotes.RUBLES5000) - 1));
                    } else {
                        throw new NoCashException("Not enough cash");
                    }
                }
                int countOf1000 = (giveOutCash - 5000 * countOf5000) / 1000;
                for (int i = 0; i < countOf1000; i++) {
                    banknotesList.add(Banknotes.RUBLES1000);
                    innerCash.put(Banknotes.RUBLES1000, (innerCash.get(Banknotes.RUBLES1000) - 1));
                }
                int countOf500 = ((giveOutCash - 5000 * countOf5000) - 1000 * countOf1000) / 500;
                for (int i = 0; i < countOf500; i++) {
                    banknotesList.add(Banknotes.RUBLES500);
                    innerCash.put(Banknotes.RUBLES500, (innerCash.get(Banknotes.RUBLES500) - 1));
                }
                int countOf100 =
                        (((giveOutCash - 5000 * countOf5000) - 1000 * countOf1000) - 500 * countOf500) / 100;
                for (int i = 0; i < countOf100; i++) {
                    banknotesList.add(Banknotes.RUBLES100);
                    innerCash.put(Banknotes.RUBLES100, (innerCash.get(Banknotes.RUBLES100) - 1));
                }
                balance -= giveOutCash;
                System.out.printf("Выданы купюры: %s\n", banknotesList);
                System.out.printf("Списано со счёта: %d\n", giveOutCash);
            } else {
                throw new RuntimeException("Not enough money on balance");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong. Try again.");
        }

        return banknotesList;
    }

    @Override
    public void getAccountBalance() {
        Integer balanceValue = Optional.of(balance)
                .orElseGet(() -> 0);
        System.out.printf("Остаток на счете: %d\n", balanceValue);
    }

}
