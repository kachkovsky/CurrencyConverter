package ru.kachkovsky.curcon.data.loader;

import android.content.Context;
import android.util.Log;

import ru.kachkovsky.curcon.data.bean.CurrencyList;
import ru.kachkovsky.curcon.data.http.HttpClient.RequestParameters;
import ru.kachkovsky.curcon.data.http.XmlResponseParser;

public class CbrDailyLoader extends AsyncBaseLoader {
    private static final String TAG = CbrDailyLoader.class.getSimpleName();

    private CacheDownloader<CurrencyList> downloader = new CacheDownloader<>();
    XmlResponseParser xmlResponseParser = new XmlResponseParser(CurrencyList.class);

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
            data = currencyList;
            if (currencyList != null) {
                deliverResult(currencyList);
            }
            forceLoad();
        }
    }

    @Override
    public Object loadInBackground() {
        Log.d(TAG, "loadInBackground");
        RequestParameters requestParameters = getRequestParameters();
        CurrencyList currencyList = downloader.doRequestOnlyFromNet(requestParameters, xmlResponseParser);
        if (currencyList == null) {
            return data;
        }
        return currencyList;
    }

    private RequestParameters getRequestParameters() {
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setUrl("http://www.cbr.ru/scripts/XML_daily.asp");
        return requestParameters;
    }
}
