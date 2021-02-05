package com.performance.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.core.view.LayoutInflaterCompat;

import android.annotation.SuppressLint;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.NetworkCapabilities;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.telephony.TelephonyScanManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
  private TextView tv_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
/*        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new LayoutInflater.Factory2() {
            @Nullable
            @Override
            public View onCreateView(View view,  String s, Context context,AttributeSet attributeSet) {
                if (TextUtils.equals("TextView",s)) {
                    view = new TextView(getApplicationContext());
                }
                return view;
            }
            @Override
            public View onCreateView(String s,Context context, AttributeSet attributeSet) {
                return null;
            }
        });*/
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        new AsyncLayoutInflater(this).inflate(R.layout.activity_main, null, new AsyncLayoutInflater.OnInflateFinishedListener() {
            @Override
            public void onInflateFinished(@NonNull View view, int resid, @Nullable ViewGroup parent) {
                setContentView(view);
            }
        });

        tv_test = findViewById(R.id.tv_test);
        initPreDrawListener(tv_test);

        getNetStates();



    }

    private void getNetStates() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        long netDataRx = 0; //接收
        long netDataTx = 0; //发送
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String subscriberId = telephonyManager.getSubscriberId();
        NetworkStats networkStats = null;
        NetworkStatsManager manager = (NetworkStatsManager) getSystemService(Context.NETWORK_STATS_SERVICE);
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        try {
            networkStats = manager.querySummary(NetworkCapabilities.TRANSPORT_WIFI, subscriberId, System.currentTimeMillis() - 1000 * 60, System.currentTimeMillis());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        while (networkStats.hasNextBucket()) {
             networkStats.getNextBucket(bucket);
             int uid = bucket.getUid();
             if (getUidByPackName() == uid) {
                netDataRx += bucket.getRxBytes();
                netDataTx += bucket.getTxPackets();
             }
        }
    }
    private int getUidByPackName() {
        int uid = -1;
        PackageManager packageManager = MainActivity.this.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            uid = packageInfo.applicationInfo.uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return uid;
    }

    private void initPreDrawListener(TextView tv_test) {
        tv_test.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                tv_test.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });


        tv_test.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
            tv_test.getViewTreeObserver().removeOnDrawListener(this);
            }
        });

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }
}