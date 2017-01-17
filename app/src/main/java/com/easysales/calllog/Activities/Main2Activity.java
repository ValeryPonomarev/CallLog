package com.easysales.calllog.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.easysales.calllog.MyApplication;
import com.easysales.calllog.R;
import com.easysales.calllog.Utils.IOHelper;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Fragment currentFragment;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //ads
        MobileAds.initialize(this, this.getString(R.string.banner_ad_unit_id));
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
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("FULBQWLZLBWGWS4H")
                .build();
        mAdView.loadAd(adRequest);
        //ads
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.openDrawer(GravityCompat.START);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        OpenCallList();
        CheckPermissions();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            OpenSettings();
            return true;
        }
        else if(id == R.id.action_refresh) {
            if(currentFragment instanceof CallListFragment)
            {
                ((CallListFragment)currentFragment).RefreshCalls();
            }
            return true;
        }
        else if(id == R.id.action_clearCallHistory) {
            MyApplication.ClearCallHistory();
            if(currentFragment instanceof CallListFragment)
            {
                ((CallListFragment)currentFragment).RefreshCalls();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        currentFragment = null;

        if (id == R.id.nav_calls) {
            OpenCallList();
        }
        else if(id == R.id.nav_settings){
            OpenSettings();
        }
        else if(id == R.id.nav_estimate){
            Estimate();
        }

        item.setCheckable(true);
        setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void OpenCallList()
    {
        CallListFragment fragment = new CallListFragment();
        OpenFragment(fragment);
        currentFragment = fragment;
    }

    private void OpenSettings()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void Estimate()
    {
        String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        }
        catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void OpenFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
    }

    private void CheckPermissions()
    {
        CheckPermission(Manifest.permission.CALL_PHONE);
        CheckPermission(Manifest.permission.PROCESS_OUTGOING_CALLS);
        CheckPermission(Manifest.permission.READ_PHONE_STATE);
        CheckPermission(Manifest.permission.RECORD_AUDIO);
        CheckPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        CheckPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        CheckPermission(Manifest.permission.READ_CONTACTS);
    }

    private void CheckPermission(String permissionName)
    {
        if(ContextCompat.checkSelfPermission(this, permissionName) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{permissionName},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }
}
