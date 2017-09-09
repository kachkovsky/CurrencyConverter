package ru.kachkovsky.curcon.data.loader;

import android.util.Log;

import ru.kachkovsky.curcon.data.http.HttpClient;
import ru.kachkovsky.curcon.data.http.HttpClient.RequestParameters.CacheMode;
import ru.kachkovsky.curcon.data.http.HttpClient.Response;
import ru.kachkovsky.curcon.data.http.ResponseParser;

public class CacheDownloader<T> {
    private static final String TAG = "CacheDownloaderTAG";

    public T doRequestOnlyFromCache(HttpClient.RequestParameters requestParams, ResponseParser<T> parser) {
        Log.d(TAG, "Start downloading from cache");
        requestParams.setUseCache(true);
        requestParams.setCacheMode(CacheMode.FROM_CACHE);
        Response<T> response = HttpClient.getInstance().doRequest(requestParams, parser);
        if (response.getError() != null) {
            return null;
        }
        Log.d(TAG, "Downloaded from cache:" + String.valueOf(response.getResult() != null));
        return response.getResult();
    }

    public T doRequestOnlyFromNet(HttpClient.RequestParameters requestParams, ResponseParser<T> parser) {
        Log.d(TAG, "Start downloading from network");
        requestParams.setUseCache(true);
        requestParams.setCacheMode(CacheMode.FROM_NET);
        Response<T> response = HttpClient.getInstance().doRequest(requestParams, parser);
        if (response.getError() != null) {
            return null;
        }
        Log.d(TAG, "Downloaded from network:" + String.valueOf(response.getResult() != null));
        return response.getResult();
    }
}
