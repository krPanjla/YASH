package com.volvain.yash;

import com.volvain.yash.DAO.Database;
import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.volvain.yash.DAO.Database;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
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

    public String firstHelpRequest(Long id,String name,Double longitude,Double latitude){

    try {
            url=new URL(serverUri+"/fstReq?id="+id+"&name="+name+"&longitude="+longitude+"&latitude="+latitude);
            con=(HttpURLConnection)url.openConnection();
            BufferedInputStream i=new BufferedInputStream(con.getInputStream());
            int b=0;
            while((b=i.read())!=-1)message+=(char)b;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return message;
    }
    public String subsequentHelpRequest(Long id,Double longitude,Double latitude){
        try {
            url=new URL(serverUri+"/subsequentReq?id="+id+"&longitude="+longitude+"&latitude="+latitude);
            con=(HttpURLConnection)url.openConnection();
            BufferedInputStream i=new BufferedInputStream(con.getInputStream());
            int b=0;
            while((b=i.read())!=-1)message+=(char)b;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;

    }

    public void sync(Long myId){
    String message="";
        try {
            url=new URL(serverUri+"/sync?id="+myId);
            con=(HttpURLConnection)url.openConnection();
            BufferedInputStream i=new BufferedInputStream(con.getInputStream());
            int b=0;
            if((b=i.read())==-1)return;
            else{
              message+=(char)b;
            while((b=i.read())!=-1)message+=(char)b;
            JSONObject obj=(JSONObject) new JSONParser().parse(message);
            Long id=Long.parseLong(obj.get("id").toString());
            Double longitude=Double.parseDouble(obj.get("longitude").toString());
            Double latitude=Double.parseDouble(obj.get("latitude").toString());
            String name=obj.get("name").toString();
            //TODO insert id,longitude,latitude in help table and send notification accordingly

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
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
          db.deletLogIn(id);
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
