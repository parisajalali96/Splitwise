package models.enums;

/*
Explanation:
- We need to define a currency enum.
- currencies in out app are some constants that we need to define them in our code once and use them in our code.
- each currency has some data, put them here and use some methods to work with currencies so simply.
 */

public enum Currency {
    GTC(1),
    SUD(2),
    QTR(5);

    private final int currencyValue;

    Currency(int currencyValue) {
        this.currencyValue = currencyValue;
    }

    public static Currency matcher(String currency) {
        if (currency.equals("GTC")) return GTC;
        if (currency.equals("SUD")) return SUD;
        if (currency.equals("QTR")) return QTR;
        return null;
    }

    public int getCurrencyValue() {
        return currencyValue;
    }
}
