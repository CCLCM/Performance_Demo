package com.performance.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
        setContentView(R.layout.activity_main);
        tv_test = findViewById(R.id.tv_test);
        initPreDrawListener(tv_test);
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