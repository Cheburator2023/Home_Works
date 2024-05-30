package ru.otus.atmemulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.atmemulator.banknotes.Banknote;
import ru.otus.atmemulator.repository.BanknoteRepository;
import ru.otus.atmemulator.exception.NoCashException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AtmService {
    private final BanknoteRepository banknoteRepository;

    @Autowired
    public AtmService(BanknoteRepository banknoteRepository) {
        this.banknoteRepository = banknoteRepository;
    }

    public void getBanknotes(List<Banknote> banknotes) {
        for (Banknote banknote : banknotes) {
            Banknote existingBanknote = banknoteRepository.findById(banknote.id()).orElseThrow();
            existingBanknote = new Banknote(existingBanknote.id(), existingBanknote.type(), existingBanknote.count() + banknote.count());
            banknoteRepository.save(existingBanknote);
        }
    }

    public List<Banknote> giveBanknotes(int giveOutCash) {
        List<Banknote> banknotesList = new ArrayList<>();
        Map<String, Integer> banknotesMap = banknoteRepository.findAll().stream()
                .collect(Collectors.toMap(Banknote::type, Banknote::count));

        giveBanknotesRecursive(giveOutCash, banknotesList, banknotesMap);

        for (Banknote banknote : banknotesList) {
            Banknote existingBanknote = banknoteRepository.findByType(banknote.type());
            existingBanknote = new Banknote(existingBanknote.id(), existingBanknote.type(), existingBanknote.count() - 1);
            banknoteRepository.save(existingBanknote);
        }

        return banknotesList;
    }

    private void giveBanknotesRecursive(int remainingCash, List<Banknote> banknotesList, Map<String, Integer> banknotesMap) {
        if (remainingCash == 0) {
            return;
        }

        Banknote maxNominal = getMaxNominalAvailable(remainingCash, banknotesMap);
        if (maxNominal != null) {
            banknotesList.add(maxNominal);
            banknotesMap.put(maxNominal.type(), banknotesMap.get(maxNominal.type()) - 1);
            giveBanknotesRecursive(remainingCash - getNominal(maxNominal), banknotesList, banknotesMap);
        } else {
            throw new NoCashException("Not enough cash");
        }
    }

    private Banknote getMaxNominalAvailable(int remainingCash, Map<String, Integer> banknotesMap) {
        Banknote maxNominal = null;
        int maxNominalValue = 0;
        for (Map.Entry<String, Integer> entry : banknotesMap.entrySet()) {
            int nominal = getNominal(entry.getKey());
            if (nominal <= remainingCash && nominal > maxNominalValue && entry.getValue() > 0) {
                maxNominal = new Banknote(null, entry.getKey(), 1);
                maxNominalValue = nominal;
            }
        }
        return maxNominal;
    }

    private int getNominal(Banknote banknote) {
        return getNominal(banknote.type());
    }

    private int getNominal(String banknoteType) {
        return Integer.parseInt(banknoteType.replaceAll("[^0-9]", ""));
    }

    public int getAccountBalance() {
        // Реализация получения баланса счета
        return 0; // Заглушка
    }
}
