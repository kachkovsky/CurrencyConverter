package ru.kachkovsky.curcon.data.loader;

import android.content.Context;

import ru.kachkovsky.curcon.data.bean.CurrencyList;
import ru.kachkovsky.curcon.data.http.HttpClient.RequestParameters;
import ru.kachkovsky.curcon.data.http.XmlResponseParser;

public class CbrDailyLoader extends AsyncBaseLoader {
    private static String TAG = CbrDailyLoader.class.getSimpleName();

    private CacheDownloader<CurrencyList> downloader = new CacheDownloader<>(CurrencyList.class);
    XmlResponseParser xmlResponseParser = new XmlResponseParser();

    public CbrDailyLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        boolean contentChanged = takeContentChanged();
        if (data != null && !contentChanged) {
            deliverResult(data);
        } else {
            RequestParameters requestParameters = getRequestParameters();
            CurrencyList currencyList = downloader.doRequestOnlyFromCache(requestParameters, xmlResponseParser);
            if (currencyList != null) {
                deliverResult(currencyList);
            }
            forceLoad();
        }
    }

    @Override
    public Object loadInBackground() {
        RequestParameters requestParameters = getRequestParameters();
        return downloader.doRequestOnlyFromNet(requestParameters, xmlResponseParser);
    }

    private RequestParameters getRequestParameters() {
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setUrl("http://www.cbr.ru/scripts/XML_daily.asp");
        return requestParameters;
    }
}
