package com.easysales.calllog.Activities.Fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.easysales.calllog.R;

/**
 * Created by drmiller on 10.07.2016.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.activity_settings);
    }
}
