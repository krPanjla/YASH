package com.volvain.yash;

import androidx.work.BackoffPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class BackgroundWork {

    public static void sync(){
        PeriodicWorkRequest req=new PeriodicWorkRequest.Builder(SyncServer.class,10, TimeUnit.SECONDS)
                            .setBackoffCriteria(BackoffPolicy.LINEAR,10,TimeUnit.SECONDS)
                             .build();
        WorkManager.getInstance().enqueue(req);
    }
}
