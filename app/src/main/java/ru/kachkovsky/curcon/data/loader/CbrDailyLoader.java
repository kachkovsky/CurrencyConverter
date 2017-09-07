package ru.kachkovsky.curcon.data.loader;

import android.content.Context;
import android.util.Log;

import ru.kachkovsky.curcon.data.bean.CurrencyList;
import ru.kachkovsky.curcon.data.http.HttpClient;
import ru.kachkovsky.curcon.data.http.HttpClient.Response;
import ru.kachkovsky.curcon.data.http.XmlResponseParser;

public class CbrDailyLoader extends AsyncBaseLoader {
    private static String TAG = CbrDailyLoader.class.getSimpleName();

    public CbrDailyLoader(Context context) {
        super(context);
    }

    @Override
    public Object loadInBackground() {
        Response response = HttpClient.getInstance().doRequest("http://www.cbr.ru/scripts/XML_daily.asp");
        if (response.getError() != null) {
            return null;
        }
        CurrencyList currencyList = null;
        try {
            currencyList = new XmlResponseParser().parseResponse(CurrencyList.class, response);
        } catch (Exception e) {
            Log.e(TAG, "xml parsing error", e);
        }
        return currencyList;
    }
}
