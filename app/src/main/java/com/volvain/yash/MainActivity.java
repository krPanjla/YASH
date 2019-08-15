package com.volvain.yash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Intent i =new Intent(this,Signup.class);
        startActivity(i);
      // String s= new Server(this).Signup(9939424667l,"GKM","148");
        //Log.i("******************","$$$$$$$$$$$$$$"+s);
       // Button b=new Button(this);
        //b.setText("Hello");
       // LinearLayout l=(LinearLayout)findViewById(R.id.lay);
        //l.addView(b);
    }





}
