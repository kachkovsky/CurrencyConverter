package ru.kachkovsky.curcon.data.loader;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ru.kachkovsky.curcon.data.http.HttpClient;
import ru.kachkovsky.curcon.data.http.HttpClient.Response;
import ru.kachkovsky.curcon.data.http.ResponseParser;

public class CacheDownloader<T> {
    private static String TAG = CacheDownloader.class.getSimpleName();

    private final Class<T> typeParameterClass;

    public CacheDownloader(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public T doRequestOnlyFromCache(Context ctx, HttpClient.RequestParameters requestParams, ResponseParser parser) {
        //requestParams.setUseCache(true);
        //requestParams.setCacheMode(CacheMode.FROM_CACHE);
        T t=null;
        try {
            String fileName = URLEncoder.encode(requestParams.getUrl(), "UTF-8");
            t = parser.parseResponse(typeParameterClass, new FileInputStream(new File(ctx.getCacheDir(), fileName)));
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "Cache error", e);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "Cache error", e);
        } catch (Exception e) {
            Log.d(TAG, "Cache error", e);
        }
        return t;
    }

    public T doRequestOnlyFromNet(Context ctx, HttpClient.RequestParameters requestParams, ResponseParser parser) {
        //requestParams.setUseCache(true);
        //requestParams.setCacheMode(CacheMode.FROM_NET);
        Response response = HttpClient.getInstance().doRequest(requestParams);
        if (response.getError() != null) {
            return null;
        }
        String x=new String(response.getBody());
        try {
            String fileName = URLEncoder.encode(requestParams.getUrl(), "UTF-8");
            FileOutputStream stream = new FileOutputStream(new File(ctx.getCacheDir(), fileName));
            try {
                stream.write(response.getBody());
            } finally {
                stream.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.getBody();
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
