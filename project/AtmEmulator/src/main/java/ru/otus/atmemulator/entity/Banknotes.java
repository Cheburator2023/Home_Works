package ru.otus.atmemulator.entity;

public enum Banknotes {
    RUBLES100(100, Currency.RUB),
    RUBLES500(500, Currency.RUB),
    RUBLES1000(1000, Currency.RUB),
    RUBLES5000(5000, Currency.RUB),
    DOLLARS50(50, Currency.DOLLAR),
    DOLLARS100(100, Currency.DOLLAR),
    EUROS50(50, Currency.EURO),
    EUROS100(100, Currency.EURO);

    private final int nominal;
    private final Currency currency;

    Banknotes(int nominal, Currency currency) {
        this.nominal = nominal;
        this.currency = currency;
    }

    public int getNominal() {
        return nominal;
    }

    public Currency getCurrency() {
        return currency;
    }
}
