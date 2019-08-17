package com.volvain.yash;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.volvain.yash.DAO.Database;


public class SyncServer extends Worker {
    Context context;
    public SyncServer(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context=context;
    }




    @NonNull
    @Override
    public Result doWork() {

        if(Global.checkInternet()==0) {
            Log.i("gauravrmsc","in Sync Server");
            Database db = new Database(context);
            Long id = db.getId();
            Log.i("gauravrmsc","id="+id);
            new Server(context).sync(id);//TODO Get Id From Database and pass it to sync
        }
        return Result.retry();
    }
}
