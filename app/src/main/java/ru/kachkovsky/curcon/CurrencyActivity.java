package ru.kachkovsky.curcon;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import ru.kachkovsky.curcon.data.bean.CurrencyList;
import ru.kachkovsky.curcon.data.loader.CbrDailyLoader;
import ru.kachkovsky.curcon.data.loader.LoaderIds;

public class CurrencyActivity extends AppCompatActivity implements LoaderCallbacks<CurrencyList> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        getSupportLoaderManager().initLoader(LoaderIds.CBR_DAILY, null, this);
    }

    @Override
    public Loader<CurrencyList> onCreateLoader(int id, Bundle args) {
        return new CbrDailyLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<CurrencyList> loader, CurrencyList data) {
        System.out.print(data);
    }

    @Override
    public void onLoaderReset(Loader<CurrencyList> loader) {

    }
}
