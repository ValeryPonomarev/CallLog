package com.easysales.calllog.Services;

import android.os.Handler;
import android.util.Log;

import com.easysales.calllog.Synchronization.Synchronizer;
import com.easysales.calllog.Utils.SettingsHelper;

import java.util.Date;
import java.util.TimerTask;

/**
 * Created by drmiller on 19.07.2016.
 */
public class MySynchronizeService extends RepeatingService {

    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    public TimerTask getTask() {
        return new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(), "Synchronize", Toast.LENGTH_SHORT).show();
                        if(SettingsHelper.getIsSynchronized()
                                && new Date().getTime() >= SettingsHelper.getLastDateSunchronization().getTime() + getPeriod()) {
                            try {
                                Synchronizer.Synchronize(getApplicationContext());
                            } catch (Exception exc) {
                                Log.d("MySynchronizeService", "Synchronize is failure: " + exc.getMessage());
                            }
                            SettingsHelper.setLastDateSunchronization(new Date());
                        }
                    }
                });
            }
        };
    }

    @Override
    public long getPeriod() {
        return SettingsHelper.getSynchronizePeriod() * 60000;
    }
}
