package com.easysales.calllog.Utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.easysales.calllog.MyApplication;

import java.util.Date;

/**
 * Created by drmiller on 20.07.2016.
 */
public final class SettingsHelper {

    public static class SettingNames
    {
        public static final String IsCallRecordName = "activity_settings_CallRecordEnabled";
        public static final String IsSyncName = "activity_settings_CallSynchronization";
        public static final String SyncPeriodName = "activity_settings_SyncPeriod";
        public static final String ServerAddressName = "activity_settings_ServerAddress";
        public static final String LastDateSyncName = "lastDateSyncName";
        public static final String MyNumberName = "activity_settings_MyNumber";
        public static final String UsageWIFIOnlyName = "activity_settings_WIFIOnly";
    }

    public static boolean getIsCallRecording() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return settings.getBoolean(SettingNames.IsCallRecordName, false);
    }

    public static boolean getIsSynchronized() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return settings.getBoolean(SettingNames.IsSyncName, false);
    }

    public static long getSynchronizePeriod()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        //try {
            return settings.getLong(SettingNames.SyncPeriodName, 30);
        /*
        } catch (ClassCastException exc) {
            String currentValue = settings.getString(SettingNames.SyncPeriodName, "30");
            long longValue = Long.parseLong(currentValue);
            settings.edit().putLong(SettingNames.SyncPeriodName, longValue).commit();
            return longValue;
        }
    /**/
    }

    public static String GetServerName()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return settings.getString(SettingNames.ServerAddressName, "http://10.0.2.2:3000/");
    }

    public static Date getLastDateSunchronization()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return new Date(settings.getLong(SettingNames.LastDateSyncName, 0));
    }

    public static void setLastDateSunchronization(Date dateSync)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        settings.edit().putLong(SettingNames.LastDateSyncName, dateSync.getTime()).commit();
    }

    public static String getMyPhoneNumber()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return settings.getString(SettingNames.MyNumberName, "+79001234567");
    }

    public static boolean getUsageWIFIOnly()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return settings.getBoolean(SettingNames.UsageWIFIOnlyName, true);
    }

}
