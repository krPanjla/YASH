package com.volvain.yash.DAO;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.volvain.yash.MainActivity;


public class Database extends SQLiteOpenHelper
{

    public static final String DatabaseName="yash.db";
    public static final String TableInfo="Login";
    public static final String TableHelp="help";
    public static final String Col1="ID";
    public static final String Col2="Name";



    public Database(Context context) {
        super(context, DatabaseName, null, 1);
    SQLiteDatabase db=this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateTableInfo = "create table Login ( ID Integer primary key, Name Text not null);";
        db.execSQL(CreateTableInfo);
        String CreateTableHelp="Create table Help (Phone_no Integer  ,Name Text ,Lat Real ,Lng Real )";
        db.execSQL(CreateTableHelp);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS Info");
        onCreate(db);
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


    public int insertHelp(Long ph,String name,double lat,double lng)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Phone_no",ph);
        cv.put("Name",name);
        cv.put("Lat",lat);
        cv.put("Lng",lng);
        long result= db.insert(TableHelp,null,cv);
        return (int) result;

    }


    public void deleteHelp(Long id)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        int res= db.delete(TableHelp,null,null);

    }

    public Cursor getHelpId()
    {
        int i;
        SQLiteDatabase db=this.getReadableDatabase();
        String Query="Select Phone_no from "+TableHelp;
        Cursor rs=db.rawQuery(Query,null);

        return rs;
    }

    public Long getSenderId()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String Query="Select Phone_no from "+TableHelp;
        Cursor rs=db.rawQuery(Query,null);
        Long i = rs.getLong(0);
        return i;
    }

    public String getSenderName()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String Query="Select name from "+TableHelp;
        Cursor rs=db.rawQuery(Query,null);
        String n=rs.getString(1);
        return n;
    }
    public void updateHelp(Long id,String name,double lat,double lng)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        String StringId=Long.toString(id);
        ContentValues cv= new ContentValues();
        Cursor rs=getHelpId();
        Long i =new Long(0);
        while(rs.moveToNext()) {
            i = rs.getLong(0);
            if (i == id) {
                cv.put("lat", lat);
                cv.put("lng", lng);
                db.update(TableHelp,cv,"Phone_no= ?",new String[]{StringId});
            }

             else{
                insertHelp(id, name, lat, lng);
               // MainActivity.getNotification();
            }
        }



    }





}

