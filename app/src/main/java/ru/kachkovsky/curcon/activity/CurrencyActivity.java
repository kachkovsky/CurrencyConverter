package ru.kachkovsky.curcon.activity;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

import ru.kachkovsky.curcon.R;
import ru.kachkovsky.curcon.activity.adapter.CurrencyAdapter;
import ru.kachkovsky.curcon.activity.helper.LayoutSwitch;
import ru.kachkovsky.curcon.data.bean.CurrencyBean;
import ru.kachkovsky.curcon.data.bean.CurrencyList;
import ru.kachkovsky.curcon.data.helper.ConversionHelper;
import ru.kachkovsky.curcon.data.helper.CurrencyBeans;
import ru.kachkovsky.curcon.data.loader.CbrDailyLoader;
import ru.kachkovsky.curcon.data.loader.LoaderIds;

public class CurrencyActivity extends AppCompatActivity implements LoaderCallbacks<CurrencyList> {
    private static String TAG = CurrencyActivity.class.getSimpleName();

    private Spinner spinnerCurrencyFrom;
    private Spinner spinnerCurrencyTo;
    private EditText editTextFrom;
    private TextView editTextTo;

    enum State {
        DATA,
        PROGRESS,
        RETRY;
    }

    private LayoutSwitch<State> layoutSwitch = new LayoutSwitch<>(
            new Pair<>(State.DATA, R.id.content),
            new Pair<>(State.PROGRESS, R.id.layoutProgress),
            new Pair<>(State.RETRY, R.id.layoutRetry)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        spinnerCurrencyFrom = (Spinner) findViewById(R.id.spinnerCurrencyFrom);
        spinnerCurrencyTo = (Spinner) findViewById(R.id.spinnerCurrencyTo);
        editTextFrom = (EditText) findViewById(R.id.editTextAmountOfCurrencyFrom);
        editTextTo = (TextView) findViewById(R.id.editTextAmountOfCurrencyTo);
        editTextTo.setTextIsSelectable(true);
        findViewById(R.id.buttonRetry).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                layoutSwitch.showLayout(CurrencyActivity.this, State.PROGRESS);
                getSupportLoaderManager().restartLoader(LoaderIds.CBR_DAILY, null, CurrencyActivity.this);
            }
        });
        findViewById(R.id.buttonApply).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                calculate();
            }
        });
        getSupportLoaderManager().initLoader(LoaderIds.CBR_DAILY, null, this);
    }

    @Override
    public Loader<CurrencyList> onCreateLoader(int id, Bundle args) {
        return new CbrDailyLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<CurrencyList> loader, CurrencyList data) {
        Log.d(TAG, "Load finished");
        if (data != null) {
            ArrayList<CurrencyBean> list = new ArrayList<>(data.getCurrencyBeanList().size() + 1);
            list.add(CurrencyBeans.getDefaultCurrencyBean());
            list.addAll(data.getCurrencyBeanList());
            spinnerCurrencyFrom.setAdapter(new CurrencyAdapter(this, list));
            spinnerCurrencyTo.setAdapter(new CurrencyAdapter(this, list));
            layoutSwitch.showLayout(CurrencyActivity.this, State.DATA);
        } else {
            layoutSwitch.showLayout(CurrencyActivity.this, State.RETRY);
        }
    }

    @Override
    public void onLoaderReset(Loader<CurrencyList> loader) {
    }

    public void calculate() {
        CurrencyBean currencyBeanFrom = (CurrencyBean) spinnerCurrencyFrom.getSelectedItem();
        CurrencyBean currencyBeanTo = (CurrencyBean) spinnerCurrencyTo.getSelectedItem();
        try {
            String valueFrom = editTextFrom.getText().toString();
            BigDecimal moneyFromDecimal = ConversionHelper.parseRussianValue(valueFrom);
            BigDecimal result = ConversionHelper.convertMoney(currencyBeanFrom, currencyBeanTo, moneyFromDecimal);
            editTextTo.setText(ConversionHelper.getRussianBigDecimalFormat().format(result));
            //editTextTo.setText(result.toPlainString());
        } catch (Exception e) {
            Log.d(TAG, "Can't convert", e);
        }
    }

}
