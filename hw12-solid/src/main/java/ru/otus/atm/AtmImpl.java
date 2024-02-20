package ru.otus.atm;

import lombok.Getter;
import lombok.Setter;
import ru.otus.banknotes.Banknotes;
import ru.otus.exception.NoCashException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class AtmImpl implements Atm {

    private final Map<Banknotes, Integer> innerCash;

    @Setter
    private int balance;


    public AtmImpl(Map<Banknotes, Integer> innerCash, int balance) {
        this.innerCash = innerCash;
        this.balance = balance;
    }

    @Override
    public void getBanknoutes(List<Banknotes> banknotes) {
        int recivedcash = 0;
        for (Banknotes banknote : banknotes) {
            innerCash.put(banknote, (innerCash.get(banknote) + 1));
            recivedcash += getNominal(banknote);
        }
        System.out.printf("Получены купюры: %s\n", banknotes);
        System.out.printf("Зачислено на счет: %d\n", balance += recivedcash);

    }

    public int getSumCashInAtm() {
        int atmCash = 0;
        for (Banknotes banknote : innerCash.keySet()) {
            atmCash += getNominal(banknote) * innerCash.get(banknote);
        }
        System.out.printf("Сумма наличности: %d\n", atmCash);
        return atmCash;
    }


    @Override
    public List<Banknotes> giveBanknotes(int giveOutCash) {
        List<Banknotes> banknotesList = new ArrayList<>();
        try {
            if (balance >= giveOutCash) {
                giveBanknotesRecursive(giveOutCash, banknotesList);
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

    private void giveBanknotesRecursive(int remainingCash, List<Banknotes> banknotesList) {
        if (remainingCash == 0) {
            return;
        }

        Banknotes maxNominal = getMaxNominalAvailable(remainingCash);
        if (maxNominal != null) {
            banknotesList.add(maxNominal);
            innerCash.put(maxNominal, innerCash.get(maxNominal) - 1);
            giveBanknotesRecursive(remainingCash - getNominal(maxNominal), banknotesList);
        } else {
            throw new NoCashException("Not enough cash");
        }
    }

    private Banknotes getMaxNominalAvailable(int remainingCash) {
        Banknotes maxNominal = null;
        int maxNominalValue = 0;
        for (Banknotes banknote : innerCash.keySet()) {
            int nominal = getNominal(banknote);
            if (nominal <= remainingCash && nominal > maxNominalValue && innerCash.get(banknote) > 0) {
                maxNominal = banknote;
                maxNominalValue = nominal;
            }
        }
        return maxNominal;
    }

    @Override
    public int getAccountBalance() {
        Integer balanceValue = Optional.of(balance)
                .orElseGet(() -> 0);
        System.out.printf("Остаток на счете: %d\n", balanceValue);
        return balanceValue;
    }

    private int getNominal(Banknotes banknotes) {
        String string = banknotes.toString();
        return Integer.parseInt(string.substring(6));
    }
}
