package com.easysales.calllog.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.easysales.calllog.Services.MySynchronizeService;
import com.easysales.calllog.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(), this.getString(R.string.banner_ad_unit_id));
        final AdView mAdView = (AdView) findViewById(R.id.adView);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                Log.d("AdView", "onAdFailedToLoad:" + String.valueOf(i));
            }

            @Override
            public void onAdLoaded() {
                Log.d("AdView", "onAdLoaded");
            }
        });

        AdRequest adRequest = new AdRequest
                .Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("lge-lg_e455-FULBQWLZLBWGWS4H")
                .addTestDevice("FULBQWLZLBWGWS4H")
                .build();
        mAdView.loadAd(adRequest);

        Button btnCallList = (Button)findViewById(R.id.btnOpenCallList);
        Button btnSettings = (Button)findViewById(R.id.main_activity_btnOpenSettings);
        Button btnEstimate = (Button) findViewById(R.id.btnEstimate);
        btnCallList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCallList();
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSettings();
            }
        });
        btnEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estimate();
            }
        });
        InitServices();
    }

    private void InitServices()
    {
        startService(new Intent(MainActivity.this, MySynchronizeService.class));
    }

    private void OpenCallList()
    {
        Intent intent = new Intent(this, CallListActivity.class);
        startActivity(intent);
    }

    private void OpenSettings()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void estimate()
    {
        String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        }
        catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
