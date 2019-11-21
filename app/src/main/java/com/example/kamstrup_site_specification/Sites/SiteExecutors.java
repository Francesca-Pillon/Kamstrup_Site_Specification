package com.example.kamstrup_site_specification.Sites;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**/
public class SiteExecutors {

    private static final Object LOCK = new Object();
    private static SiteExecutors sInstance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;

    private SiteExecutors(Executor diskIO, Executor networkIO, Executor mainThread){
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }


    public static SiteExecutors getInstance(){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new SiteExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor diskIO(){
        return diskIO;
    }

    public Executor mainThread(){
        return mainThread;
    }

    public Executor networkIO(){
        return networkIO;
    }

    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());


        @Override
        public void execute(Runnable runnable) {
            mainThreadHandler.post(runnable);
        }

    }





}
