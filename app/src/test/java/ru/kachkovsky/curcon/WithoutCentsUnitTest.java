package ru.kachkovsky.curcon;

import org.junit.Test;

import java.math.BigDecimal;

import ru.kachkovsky.curcon.data.bean.CurrencyBean;
import ru.kachkovsky.curcon.data.helper.ConversionHelper;
import ru.kachkovsky.curcon.data.helper.CurrencyBeans;

import static org.junit.Assert.assertEquals;

public class WithoutCentsUnitTest {

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
    public void twoSignsWithoutCents() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(CurrencyBeans.getDefaultCurrencyBean(), getTestCurrencyJPY(), ConversionHelper.parseRussianValue("44,44"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("84"));
    }

    @Test
    public void threeSignsWithoutCents() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(CurrencyBeans.getDefaultCurrencyBean(), getTestCurrencyJPY(), ConversionHelper.parseRussianValue("44,244"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("84"));
    }

    @Test
    public void oneSignsWithoutCents() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(CurrencyBeans.getDefaultCurrencyBean(), getTestCurrencyJPY(), ConversionHelper.parseRussianValue("64,2"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("122"));
    }

    @Test
    public void threeSignsWithoutCentsFiveAtEndAfterFour() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(CurrencyBeans.getDefaultCurrencyBean(), getTestCurrencyJPY(), ConversionHelper.parseRussianValue("34,445"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("65"));
    }

    @Test
    public void threeSignsWithoutCentsFiveAtEndAfterSeven() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(CurrencyBeans.getDefaultCurrencyBean(), getTestCurrencyJPY(), ConversionHelper.parseRussianValue("54,475"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("103"));
    }

    @Test
    public void zeroAfterZero() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(CurrencyBeans.getDefaultCurrencyBean(), getTestCurrencyJPY(), ConversionHelper.parseRussianValue("0"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("0"));
    }
}
