package com.performance.demo;

import android.net.TrafficStats;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadPollUtils {
    public static ExecutorService getService(){
        return sService;
    }

    private static ExecutorService sService = Executors.newFixedThreadPool(5, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("");
            return thread;
        }
    });



}
