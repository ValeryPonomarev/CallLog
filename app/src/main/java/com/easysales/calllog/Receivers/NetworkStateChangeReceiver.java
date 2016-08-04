package com.easysales.calllog.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.easysales.calllog.Synchronization.Synchronizer;
import com.easysales.calllog.Utils.SettingsHelper;

import java.util.Date;

public class NetworkStateChangeReceiver extends BroadcastReceiver {
    public NetworkStateChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(new Date().getTime() < SettingsHelper.getLastDateSunchronization().getTime() + SettingsHelper.getSynchronizePeriod() && checkInternetConnection(context)){
            Synchronizer.Synchronize(context);
        }
    }

    private boolean checkInternetConnection(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if(activeNetwork != null) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE
                    && !SettingsHelper.getUsageWIFIOnly())
            {
                return true;
            }
        }
        return false;
    }
}
