package ru.kachkovsky.curcon;

import org.junit.Test;

import java.math.BigDecimal;

import ru.kachkovsky.curcon.data.bean.CurrencyBean;
import ru.kachkovsky.curcon.data.helper.ConversionHelper;
import ru.kachkovsky.curcon.data.helper.CurrencyBeans;

import static org.junit.Assert.assertEquals;

public class ComplicatedParametersUnitTest {

    public static CurrencyBean getTestCurrencyUSD() {
        CurrencyBean cb = new CurrencyBean();
        cb.setNominal("1");
        cb.setCharCode("USD");
        cb.setNumCode("840");
        cb.setName("Доллар США");
        cb.setValue("57,1694");
        return cb;
    }

    public static CurrencyBean getTestCurrencyJPY() {
        CurrencyBean cb = new CurrencyBean();
        cb.setNominal("100");
        cb.setCharCode("JPY");
        cb.setNumCode("392");
        cb.setName("Японских иен");
        cb.setValue("52,7077");
        return cb;
    }

    @Test
    public void twoSignsTwoCurrenciesWithDifferentParameters() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(getTestCurrencyJPY(),getTestCurrencyUSD(), ConversionHelper.parseRussianValue("44,44"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("0,41"));
    }

    @Test
    public void threeSignsTwoCurrenciesWithDifferentParameters() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(getTestCurrencyJPY(),getTestCurrencyUSD(), ConversionHelper.parseRussianValue("44,244"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("0,41"));
    }

    @Test
    public void oneSignsTwoCurrenciesWithDifferentParameters() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(getTestCurrencyJPY(),getTestCurrencyUSD(), ConversionHelper.parseRussianValue("64,2"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("0,59"));
    }

    @Test
    public void threeSignsTwoCurrenciesWithDifferentParametersFiveAtEndAfterFour() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(getTestCurrencyJPY(),getTestCurrencyUSD(), ConversionHelper.parseRussianValue("34,445"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("0,32"));
    }

    @Test
    public void threeSignsTwoCurrenciesWithDifferentParametersFiveAtEndAfterSeven() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(getTestCurrencyJPY(),getTestCurrencyUSD(), ConversionHelper.parseRussianValue("54,475"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("0,50"));
    }

    @Test
    public void zeroAfterZero() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(getTestCurrencyJPY(),getTestCurrencyUSD(), ConversionHelper.parseRussianValue("0"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("0,00"));
    }
}
