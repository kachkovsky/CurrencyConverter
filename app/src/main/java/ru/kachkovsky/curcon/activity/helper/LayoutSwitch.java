package ru.kachkovsky.curcon.activity.helper;

import android.app.Activity;
import android.util.Pair;
import android.view.View;

public class LayoutSwitch<T> {

    private Pair<T, Integer>[] objectForIds;

    private T currentState = null;

    public LayoutSwitch(Pair<T, Integer>... objectForIds) {
        this.objectForIds = objectForIds;
    }

    public void showLayout(Activity activity, T object) {
        if (object.equals(currentState)) {
            return;
        }
        Pair<T, Integer> doVisible = null;
        for (Pair<T, Integer> objectForId : objectForIds) {
            if (objectForId.first.equals(object)) {
                doVisible = objectForId;
            } else {
                activity.findViewById(objectForId.second).setVisibility(View.GONE);
            }
        }
        if (doVisible != null) {
            activity.findViewById(doVisible.second).setVisibility(View.VISIBLE);
        }
        currentState = object;
    }
}
