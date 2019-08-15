package com.volvain.yash;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;


public class Server  {
Context c;
URL url;
HttpURLConnection con;
Server(Context c){
    this.c=c;
}
    public  String Signup(Long phone,String name,String password){
    String message="";
        String serverUri=c.getString(R.string.server);
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
}
