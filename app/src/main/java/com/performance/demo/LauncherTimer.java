package com.performance.demo;

import android.util.Log;

public class LauncherTimer {
    public static final String TAG = LauncherTimer.class.getSimpleName()+"PerF";
  private  static long sTime;
  public static void startRecord() {
      sTime = System.currentTimeMillis();

  }

  public static void endRecord() {
      long cois = System.currentTimeMillis() - sTime;
      Log.d(TAG," cost " + cois);
  }
}
