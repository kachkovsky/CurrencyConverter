package ru.kachkovsky.curcon;

import org.junit.Test;

import java.math.BigDecimal;

import ru.kachkovsky.curcon.data.helper.ConversionHelper;
import ru.kachkovsky.curcon.data.helper.CurrencyBeans;

import static org.junit.Assert.*;

public class CalculatorUnitTest {
    @Test
    public void twoSignsOneCurrency() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(CurrencyBeans.getDefaultCurrencyBean(), CurrencyBeans.getDefaultCurrencyBean(), ConversionHelper.parseRussianValue("44,44"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("44,44"));
    }

    @Test
    public void threeSignsOneCurrency() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(CurrencyBeans.getDefaultCurrencyBean(), CurrencyBeans.getDefaultCurrencyBean(), ConversionHelper.parseRussianValue("44,244"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("44,24"));
    }

    @Test
    public void oneSignsOneCurrency() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(CurrencyBeans.getDefaultCurrencyBean(), CurrencyBeans.getDefaultCurrencyBean(), ConversionHelper.parseRussianValue("44,2"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("44,20"));
    }

    @Test
    public void threeSignsOneCurrencyFiveAtEndAfterFour() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(CurrencyBeans.getDefaultCurrencyBean(), CurrencyBeans.getDefaultCurrencyBean(), ConversionHelper.parseRussianValue("44,445"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("44,44"));
    }

    @Test
    public void threeSignsOneCurrencyFiveAtEndAfterSeven() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(CurrencyBeans.getDefaultCurrencyBean(), CurrencyBeans.getDefaultCurrencyBean(), ConversionHelper.parseRussianValue("44,475"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("44,48"));
    }

    @Test
    public void zeroAfterZero() throws Exception {
        BigDecimal bigDecimal = ConversionHelper.convertMoney(CurrencyBeans.getDefaultCurrencyBean(), CurrencyBeans.getDefaultCurrencyBean(), ConversionHelper.parseRussianValue("0"));
        assertEquals(bigDecimal,ConversionHelper.parseRussianValue("0,00"));
    }
}