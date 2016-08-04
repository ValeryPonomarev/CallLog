package com.easysales.calllog;

import android.app.Application;
import android.content.Context;

/**
 * Created by drmiller on 23.07.2016.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getContext() {
        return MyApplication.context;
    }

}
