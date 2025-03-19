package models.enums;

/*
Explanation:
- We need to define a currency enum.
- currencies in out app are some constants that we need to define them in our code once and use them in our code.
- each currency has some data, put them here and use some methods to work with currencies so simply.
 */

public enum Currency {
    GTC("GTC"),
    SUD("SUD"),
    QTR("QTR");

    private final String currency;

    Currency(String currency) {
        this.currency = currency;
    }

    public static Currency matcher(String currency) {
        if (currency.equals("GTC")) return GTC;
        if (currency.equals("SUD")) return SUD;
        if (currency.equals("QTR")) return QTR;
        return null;
    }
   /* public static String string(Currency currency) {
        switch (currency) {
            case SUD:{
                return "USD";
            }
            case QTR:{
                return "QTR";
            }
            case GTC:{
                return "GTC";
            }
        }
        return "";
    }
    */

}
