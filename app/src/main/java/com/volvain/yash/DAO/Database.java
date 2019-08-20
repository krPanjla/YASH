package com.volvain.yash.DAO;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.volvain.yash.MainActivity;
import com.volvain.yash.R;


public class Database extends SQLiteOpenHelper
{   public  Cursor rs;
    public SQLiteDatabase db;
    public static final String DatabaseName="yash.db";
    public static final String TableInfo="Login";
    public static final String TableHelp="Help";
    public static final String Col1="ID";
    public static final String Col2="Name";
    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    String name;
    private Context context;


    public Database(Context context) {
        super(context, DatabaseName, null, 1);
     db=this.getWritableDatabase();
    this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateTableInfo = "create table Login ( ID Integer primary key, Name Text not null);";
        db.execSQL(CreateTableInfo);
        String CreateTableHelp="Create table Help (Phone_no Integer  ,Name Text ,Lng Real ,Lat Real )";
        db.execSQL(CreateTableHelp);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //db.execSQL(" DROP TABLE IF EXISTS Info");
        //onCreate(db);
    }

    public  void insertData(Long id, String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(Col1,id);
        cv.put(Col2,name);
        db.insert(TableInfo,null,cv);

    }


    public String getName(Long id)
    {
        String nm="";
        Long i=new Long(0);
        SQLiteDatabase db=this.getReadableDatabase();
        String Query ="Select * from Login where id= "+ id;
        Cursor rs= db.rawQuery(Query,null);
        while (rs.moveToNext())
        {
            i= rs.getLong(0);
            nm=rs.getString(1);
        }
        if(i==id)
            return nm;

        else
            return "no_such_id_exist";

    }

    public Long getId()
    {
        Long i=0L;
        SQLiteDatabase db=this.getReadableDatabase();
        String Query="Select id from "+TableInfo;
        Cursor rs=db.rawQuery(Query,null);
        while(rs.moveToNext())
            i= rs.getLong(0);
        return i;
    }

    public boolean checkId()
    {
        Long i=0L;

        String Query="Select id from "+TableInfo;
        Cursor rs=db.rawQuery(Query,null);
        return rs.moveToNext();
    }

    public void updateName(int id,String nm)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        String StringId=Integer.toString(id);
        ContentValues cv= new ContentValues();
        cv.put(Col2,nm);
        db.update(TableInfo,cv,"ID = ?",new String[]{StringId});
    }


    public  void deletLogIn(Long id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
      //Cursor rs = null;
      //if(rs.getCount()>0) {
          int res = db.delete(TableInfo, null, null);
     // }

    }


    public void insertHelp(Long ph,String name,double lng,double lat)
    {Log.i("gauravrmsc","in insert");
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Phone_no",ph);
        cv.put("Name",name);
        cv.put("Lng",lng);
        cv.put("Lat",lat);

        db.insert(TableHelp,null,cv);

        getNotification();

       // return (int) result;

    }


    public void deleteHelp(Long id)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        int res= db.delete(TableHelp,null,null);

    }

    public Long getHelpId()
    {
        Long i =0L;
        SQLiteDatabase db=this.getReadableDatabase();
        String Query="Select Phone_no from "+TableHelp;
        Cursor rs =db.rawQuery(Query,null);
        while(rs.moveToNext())
            i=rs.getLong(0);
       return i;

    }

    public String getHelpName()
    {
        String n="";
        SQLiteDatabase db=this.getReadableDatabase();
        String Query="Select name from "+TableHelp;
        Cursor rs =db.rawQuery(Query,null);
        while(rs.moveToNext())
            n=rs.getString(0);
        return n;
    }



    public Cursor getCursor()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String Query="Select * from "+TableHelp;
        Cursor rs =db.rawQuery(Query,null);
       // rs.moveToFirst();
        return rs;
    }

    public Long getSenderId()
    {
        Long i=0L;
        SQLiteDatabase db=this.getReadableDatabase();
        String Query="Select * from "+TableInfo;
        Cursor rs=db.rawQuery(Query,null);
        while ((rs.moveToNext()))
         i = rs.getLong(0);
        return i;
    }

    public String getSenderName()
    {
        String n="";
        SQLiteDatabase db=this.getReadableDatabase();
        String Query="Select * from "+TableInfo;
        Cursor rs=db.rawQuery(Query,null);
        while ((rs.moveToNext()))
         n=rs.getString(1);
        return n;
    }
    public void updatHelp(Long id,String name,double lng,double lat)
    {this.name=name;

        SQLiteDatabase db=this.getWritableDatabase();

        String StringId=Long.toString(id);
        ContentValues cv= new ContentValues();
        //Cursor rs=getHelpId();
        Long i=0L;
        String Query="Select Phone_no from "+TableHelp;
        Cursor rs =db.rawQuery(Query,null);
      //  Log.i("gauravrmsc","checking Value "+id);
        //Log.i("gauravrmsc","checking Value "+rs.moveToNext());

        while(rs.moveToNext()) {
            i = rs.getLong(0);

            if (i.equals(id)) {
                cv.put("lat", lat);
                cv.put("lng", lng);
                db.update(TableHelp, cv, "Phone_no= ?", new String[]{StringId});

                return;
            }

        }

            insertHelp(id, name, lng, lat);


        }
      


    public void getNotification(){

    /*NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"Yash")
            .setContentTitle("New Request")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Log.i("gauravrmsc"," n"+builder);
    NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        Log.i("gauravrmsc"," notificati");
    manager.notify(1,builder.build());
        Log.i("gauravrmsc"," notification sent");
  */
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("");
        bigText.setBigContentTitle("Today's Bible Verse");
        bigText.setSummaryText("Text in detail");
Notification notification= new NotificationCompat.Builder(context,"CHANNELID")
        .setContentTitle("New Request")
        .setContentText(name+" Needs Help")
        .setAutoCancel(true)
        .setSmallIcon(R.drawable.abc)
        .setPriority(NotificationCompat.PRIORITY_HIGH).build();

   NotificationManagerCompat notificationManager=NotificationManagerCompat.from(context);

   notificationManager.notify(1,notification);

    }


}




