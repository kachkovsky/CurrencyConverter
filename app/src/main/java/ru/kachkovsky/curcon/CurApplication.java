package ru.kachkovsky.curcon;

import android.app.Application;
import android.net.http.HttpResponseCache;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class CurApplication extends Application {
    private static String TAG = CurApplication.class.getSimpleName();
    public void onCreate() {
        super.onCreate();
        try {
            File httpCacheDir = new File(this.getCacheDir(), "http");
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (IOException e) {
            Log.i(TAG, "HTTP response cache installation failed:", e);
        }
    }
}
