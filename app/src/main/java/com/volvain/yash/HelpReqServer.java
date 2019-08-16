package com.volvain.yash;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class HelpReqServer extends Worker {
    Context context;
    public HelpReqServer(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context=context;
    }
    @NonNull
    @Override
    public Result doWork() {
        String message;
        int n=getInputData().getInt("n",1);
        if(n==0){
        Long id=getInputData().getLong("id",-1);
        String name=getInputData().getString("name");
        Double longitude=getInputData().getDouble("longitude",360);
        Double latitude=getInputData().getDouble("latitude",360);
        if(!(id==-1||longitude==360||latitude==360)){
             message=new Server().firstHelpRequest(id,name,longitude,latitude);
            Data out=new Data.Builder()
                    .putString("message",message)
                    .build();
            if(message.equals("Request Received")) return Result.success(out);
            else return Result.retry();
        }
         }
        else{
            Long id=getInputData().getLong("id",-1);
            Double longitude=getInputData().getDouble("longitude",360);
            Double latitude=getInputData().getDouble("latitude",360);
            if(!(id==-1||longitude==360||latitude==360)){
                message=new Server().subsequentHelpRequest(id,longitude,latitude);
                Data out=new Data.Builder()
                        .putString("message",message)
                        .build();
                if(message.equals("Request Received")) return Result.success(out);
                else return Result.retry();
            }
        }
     return Result.retry();
    }
}
