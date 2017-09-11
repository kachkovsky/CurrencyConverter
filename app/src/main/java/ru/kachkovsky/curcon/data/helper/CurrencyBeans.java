package ru.kachkovsky.curcon.data.helper;

import ru.kachkovsky.curcon.data.bean.CurrencyBean;

public class CurrencyBeans {

    public static final String RUB_CHAR_CODE = "RUB";

    public static CurrencyBean getDefaultCurrencyBean() {
        CurrencyBean cb = new CurrencyBean();
        cb.setNominal("1");
        cb.setCharCode(RUB_CHAR_CODE);
        cb.setNumCode("643");
        cb.setName("Российский рубль");
        cb.setValue("1");
        return cb;
    }
}
