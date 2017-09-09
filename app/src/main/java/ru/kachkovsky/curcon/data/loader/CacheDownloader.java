package ru.kachkovsky.curcon.data.loader;

import android.util.Log;

import ru.kachkovsky.curcon.data.http.HttpClient;
import ru.kachkovsky.curcon.data.http.HttpClient.RequestParameters.CacheMode;
import ru.kachkovsky.curcon.data.http.HttpClient.Response;
import ru.kachkovsky.curcon.data.http.ResponseParser;

public class CacheDownloader<T> {
    private static String TAG = CacheDownloader.class.getSimpleName();

    private final Class<T> typeParameterClass;

    public CacheDownloader(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public T doRequestOnlyFromCache(HttpClient.RequestParameters requestParams, ResponseParser parser) {
        requestParams.setUseCache(true);
        requestParams.setCacheMode(CacheMode.FROM_CACHE);
        Response response = HttpClient.getInstance().doRequest(requestParams);
        if (response.getError() != null) {
            return null;
        }
        return parseResponse(parser, response);
    }

    public T doRequestOnlyFromNet(HttpClient.RequestParameters requestParams, ResponseParser parser) {
        requestParams.setUseCache(true);
        requestParams.setCacheMode(CacheMode.FROM_NET);
        Response response = HttpClient.getInstance().doRequest(requestParams);
        if (response.getError() != null) {
            return null;
        }
        return parseResponse(parser, response);
    }

    private T parseResponse(ResponseParser parser, Response response) {
        T data = null;
        try {
            data = parser.parseResponse(typeParameterClass, response);
        } catch (Exception e) {
            Log.e(TAG, "Parsing error", e);
        }
        return data;
    }
}
