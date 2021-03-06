package ru.kachkovsky.curcon.data.bean;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="ValCurs")
public class CurrencyList {
    @Attribute(name="Date")
    private String date;
    @Attribute(name="name")
    private String name;
    @ElementList(name="Valute", inline = true)
    private List<CurrencyBean> currencyBeanList;

    public List<CurrencyBean> getCurrencyBeanList() {
        return currencyBeanList;
    }

    public void setCurrencyBeanList(List<CurrencyBean> currencyBeanList) {
        this.currencyBeanList = currencyBeanList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
