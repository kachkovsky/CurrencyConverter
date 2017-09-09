package ru.kachkovsky.curcon.data.loader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public abstract class AsyncBaseLoader<T> extends AsyncTaskLoader<T> {

    protected T data;

    public AsyncBaseLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        boolean contentChanged = takeContentChanged();
        if (data != null && !contentChanged) {
            deliverResult(data);
        } else {
            forceLoad();
        }
    }

    @Override
    protected T onLoadInBackground() {
        data = super.onLoadInBackground();
        return data;
    }

    @Override
    public void stopLoading() {
        super.stopLoading();
        cancelLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        cancelLoad();
    }

}
