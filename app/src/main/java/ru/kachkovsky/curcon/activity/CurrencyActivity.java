package ru.kachkovsky.curcon.activity;

import android.icu.util.Currency;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;

import java.math.BigDecimal;

import ru.kachkovsky.curcon.R;
import ru.kachkovsky.curcon.activity.adapter.CurrencyAdapter;
import ru.kachkovsky.curcon.data.bean.CurrencyBean;
import ru.kachkovsky.curcon.data.bean.CurrencyList;
import ru.kachkovsky.curcon.data.loader.CbrDailyLoader;
import ru.kachkovsky.curcon.data.loader.LoaderIds;

public class CurrencyActivity extends AppCompatActivity implements LoaderCallbacks<CurrencyList> {
    private static String TAG = CurrencyActivity.class.getSimpleName();

    private Spinner spinnerCurrencyFrom;
    private Spinner spinnerCurrencyTo;
    private EditText editTextFrom;
    private EditText editTextTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        spinnerCurrencyFrom = (Spinner) findViewById(R.id.spinnerCurrencyFrom);
        spinnerCurrencyTo = (Spinner) findViewById(R.id.spinnerCurrencyTo);
        editTextFrom = (EditText) findViewById(R.id.editTextAmountOfCurrencyFrom);
        editTextTo = (EditText) findViewById(R.id.editTextAmountOfCurrencyFrom);

        spinnerCurrencyFrom.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                calculate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                calculate();
            }
        });
        spinnerCurrencyTo.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                calculate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                calculate();
            }
        });
        editTextFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
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
        spinnerCurrencyFrom.setAdapter(new CurrencyAdapter(this, data.getCurrencyBeanList()));
        spinnerCurrencyTo.setAdapter(new CurrencyAdapter(this, data.getCurrencyBeanList()));
    }

    @Override
    public void onLoaderReset(Loader<CurrencyList> loader) {
    }

    public void calculate() {
        CurrencyBean currencyBeanFrom =(CurrencyBean) spinnerCurrencyFrom.getSelectedItem();
        CurrencyBean currencyBeanTo =(CurrencyBean) spinnerCurrencyTo.getSelectedItem();
        Editable text = editTextFrom.getText();
        

    }
}
