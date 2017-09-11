package ru.kachkovsky.curcon.data.helper;

import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

import ru.kachkovsky.curcon.activity.CurrencyActivity;
import ru.kachkovsky.curcon.data.bean.CurrencyBean;

public class ConversionHelper {
    private static String TAG = ConversionHelper.class.getSimpleName();


    private static final DecimalFormat RU_DECIMAL_FORMAT;

    static {
        RU_DECIMAL_FORMAT = new DecimalFormat("0.##", new DecimalFormatSymbols(new Locale("ru")));
        RU_DECIMAL_FORMAT.setParseBigDecimal(true);
    }

    public static BigDecimal convertMoney(CurrencyBean currencyBeanFrom, CurrencyBean currencyBeanTo, BigDecimal moneyFromDecimal) throws ParseException {
        BigDecimal nominalFromDecimal = new BigDecimal(currencyBeanFrom.getNominal());
        BigDecimal rateFromDecimal = parseRussianValue(currencyBeanFrom.getValue());

        BigDecimal nominalToDecimal = new BigDecimal(currencyBeanTo.getNominal());
        BigDecimal rateToDecimal = parseRussianValue(currencyBeanTo.getValue());

        BigDecimal dividend = moneyFromDecimal.multiply(nominalToDecimal).multiply(rateFromDecimal);
        BigDecimal divisor = nominalFromDecimal.multiply(rateToDecimal);
        Currency currencyTo;
        try {
            currencyTo = Currency.getInstance(currencyBeanTo.getCharCode());
        } catch (IllegalArgumentException e) {
            Log.i(TAG, "Char code not found");
            currencyTo = Currency.getInstance(CurrencyBeans.RUB_CHAR_CODE);
        }

        return dividend.divide(divisor, currencyTo.getDefaultFractionDigits(), BigDecimal.ROUND_HALF_EVEN);
    }

    public static DecimalFormat getRussianBigDecimalFormat() {
        return RU_DECIMAL_FORMAT;
    }

    public static BigDecimal parseRussianValue(String value) throws ParseException {
        DecimalFormat fmt = getRussianBigDecimalFormat();
        return (BigDecimal) fmt.parse(value);
    }
}
