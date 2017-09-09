package ru.kachkovsky.curcon.data.loader;

import ru.kachkovsky.curcon.data.http.HttpClient;
import ru.kachkovsky.curcon.data.http.HttpClient.RequestParameters.CacheMode;
import ru.kachkovsky.curcon.data.http.HttpClient.Response;
import ru.kachkovsky.curcon.data.http.ResponseParser;

public class CacheDownloader<T> {
    private static String TAG = CacheDownloader.class.getSimpleName();

    public T doRequestOnlyFromCache(HttpClient.RequestParameters requestParams, ResponseParser<T> parser) {
        requestParams.setUseCache(true);
        requestParams.setCacheMode(CacheMode.FROM_CACHE);
        Response<T> response = HttpClient.getInstance().doRequest(requestParams, parser);
        if (response.getError() != null) {
            return null;
        }
        return response.getResult();
    }

    public T doRequestOnlyFromNet(HttpClient.RequestParameters requestParams, ResponseParser<T> parser) {
        requestParams.setUseCache(true);
        requestParams.setCacheMode(CacheMode.FROM_NET);
        Response<T> response = HttpClient.getInstance().doRequest(requestParams, parser);
        if (response.getError() != null) {
            return null;
        }
        return response.getResult();
    }
}
