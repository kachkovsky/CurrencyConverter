package ru.kachkovsky.curcon.activity.helper;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class NetworkLayoutSwitch {
    private int dataLayoutId;
    private int retryLayoutId;
    private int progressLayoutId;

    enum State {
        DATA,
        PROGRESS,
        RETRY;
    }

    private State currentState = null;

    public NetworkLayoutSwitch(int dataLayoutId, int progressLayoutId, int retryLayoutId) {
        this.dataLayoutId = dataLayoutId;
        this.retryLayoutId = retryLayoutId;
        this.progressLayoutId = progressLayoutId;
    }

    public void showRetryLayout(Activity activity) {
        if (State.RETRY.equals(currentState)) {
            return;
        }
        activity.findViewById(dataLayoutId).setVisibility(View.GONE);
        activity.findViewById(progressLayoutId).setVisibility(View.GONE);
        activity.findViewById(retryLayoutId).setVisibility(View.VISIBLE);
        currentState = State.RETRY;
    }

    public void showProgressLayout(Activity activity) {
        if (State.PROGRESS.equals(currentState)) {
            return;
        }
        activity.findViewById(dataLayoutId).setVisibility(View.GONE);
        activity.findViewById(retryLayoutId).setVisibility(View.GONE);
        activity.findViewById(progressLayoutId).setVisibility(View.VISIBLE);
        currentState = State.PROGRESS;
    }

    public void showDataLayout(Activity activity) {
        if (State.DATA.equals(currentState)) {
            return;
        }
        activity.findViewById(retryLayoutId).setVisibility(View.GONE);
        activity.findViewById(progressLayoutId).setVisibility(View.GONE);
        activity.findViewById(dataLayoutId).setVisibility(View.VISIBLE);
        currentState = State.DATA;
    }
}
