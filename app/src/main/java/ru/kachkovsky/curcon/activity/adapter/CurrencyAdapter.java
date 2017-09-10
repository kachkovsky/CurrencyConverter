package ru.kachkovsky.curcon.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.kachkovsky.curcon.R;
import ru.kachkovsky.curcon.data.bean.CurrencyBean;

public class CurrencyAdapter extends ArrayAdapter<CurrencyBean> {

    public CurrencyAdapter(@NonNull Context context, @NonNull List<CurrencyBean> objects) {
        super(context, R.layout.item_currency, R.id.textViewCurrencyName, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,@NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_currency, parent, false);
        } else {
            view = convertView;
        }
        TextView textView = (TextView) view.findViewById(R.id.textViewCurrencyName);
        CurrencyBean item = getItem(position);
        textView.setText(item.getName() + ", " + item.getCharCode());
        return view;
    }

};