package com.easysales.calllog.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public abstract class RepeatingService extends Service {

    private static Timer timer;
    private final String LOG_TAG = "RepeatingService";

    public RepeatingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "Create service");
        if(timer != null){
            timer.cancel();
        }
        else {
            timer = new Timer();
        }

        timer.scheduleAtFixedRate(getTask(), 0, getPeriod());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public abstract TimerTask getTask();
    public abstract long getPeriod();
}
