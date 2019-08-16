package com.volvain.yash;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SyncServer extends Worker {
    Context context;
    public SyncServer(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context=context;
    }

    @NonNull
    @Override
    public Result doWork() {

      //  if(Global.checkInternet()==0)
      //  new Server(context).sync();//TODO Get Id From Database and pass it to sync
        return Result.retry();
    }
}
