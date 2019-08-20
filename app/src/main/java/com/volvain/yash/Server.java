package com.volvain.yash;

import com.volvain.yash.DAO.Database;
import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import androidx.work.WorkManager;

import com.volvain.yash.DAO.Database;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Map;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Server  {

URL url;
HttpURLConnection con;
static String serverUri;
String message="";
Context context;
Server(Context context){
this.context=context;
serverUri=context.getString(R.string.server);
}

    public  String Signup(Long phone,String name,String password){


        try {
          url =new URL(serverUri+"/signup?phone="+phone+"&name="+name+"&password="+password);
          con=(HttpURLConnection) url.openConnection();
            BufferedInputStream i=new BufferedInputStream(con.getInputStream());
            int b=0;
            while((b=i.read())!=-1)message+=(char)b;
        } catch (MalformedURLException e) {
            return e.toString();
        } catch (IOException e) {
            Log.i("Error",url.toString());
            return e.toString();
        }

        return message;
    }

    public int firstHelpRequest(Long id,String name,Double longitude,Double latitude){
        Log.i("gauravrmsc","Sending first request");
        int m=-1;
    try {
            url=new URL(serverUri+"/fstReq?id="+id+"&name="+name+"&longitude="+longitude+"&latitude="+latitude);

            con=(HttpURLConnection)url.openConnection();
            BufferedInputStream i=new BufferedInputStream(con.getInputStream());
        Log.i("gauravrmsc","received first request");
           /* int b=-;
            while((b=i.read())!=-1)message+=(char)b;*/
           m=Integer.parseInt(""+(char)i.read());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return m;
    //return message;
    }
    public int subsequentHelpRequest(Long id,Double longitude,Double latitude){
        Log.i("gauravrmsc","Sending Subsequent request");
        int m=-2;
        try {

            url=new URL(serverUri+"/subsequentReq?id="+id+"&longitude="+longitude+"&latitude="+latitude);
            con=(HttpURLConnection)url.openConnection();
            BufferedInputStream i=new BufferedInputStream(con.getInputStream());
            Log.i("gauravrmsc","received Subsequent request");
            /*int b=0;
            while((b=i.read())!=-1)message+=(char)b;*/
            m=Integer.parseInt(""+(char)i.read());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("gauravrmsc",""+e);
            e.printStackTrace();
        }
        return m;

    }

    public void sync(Long myId){
    String message="";

        try {
            url=new URL(serverUri+"/sync?id="+myId);
            con=(HttpURLConnection)url.openConnection();
            BufferedInputStream i=new BufferedInputStream(con.getInputStream());
            Log.i("gauravrmsc","response received");
            int b=0;
          /*  if((b=i.read())==-1){
                Log.i("gauravrmsc","Server sent null");
                return;
            }
            else{
              message+=(char)b;*/
            while((b=i.read())!=-1)message+=(char)b;
            Log.i("gauravrmsc","response received="+message);
            JSONArray ary=(JSONArray) new JSONParser().parse(message);

            for (int n=0;n<ary.size();n++) {
                Map m=(Map)ary.get(n);
              /*  Long id = Long.parseLong(m.get("id").toString());
                Double longitude = Double.parseDouble(m.get("longitude").toString());
                Double latitude = Double.parseDouble(obj.get("latitude").toString());
                String name = obj.get("name").toString();*/
              Long id=(Long)m.get("id");
              Double longitude=(Double)m.get("longitude");
                Double latitude=(Double)m.get("latitude");
                String name=m.get("name").toString();
                Log.i("gauravrmsc","longitude="+longitude);

                //TODO insert id,longitude,latitude and name in help table and send notification accordingly

                Database db= new Database(context);

                db.updatHelp(id,name,longitude,latitude);

            }
            //}
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("gauravrmsc",""+e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("gauravrmsc",""+e);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("gauravrmsc","Parse Exception"+e);
        }
    }
  public boolean login(Long id,String password){

      try {

          url=new URL(serverUri+"/login?id="+id+"&password="+password);
          con=(HttpURLConnection)url.openConnection();
          BufferedInputStream i=new BufferedInputStream(con.getInputStream());

          int b=0;
          while((b=i.read())!=-1)message+=(char)b;
          if(!(message.equals("Invalid Password") || message.equals("Invalid User Name"))){
          //TODO put name and id in database and remove if any exist before
          Database db= new Database(context);

          db.deleteHelp();//TODO add to logout
          db.deletLogIn();//TODO add to logout


          db.insertData(id,message);


          return true;}
      } catch (MalformedURLException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
      return false;
  }
}
