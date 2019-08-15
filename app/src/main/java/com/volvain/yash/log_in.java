package com.volvain.yash;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class log_in extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }


    public void statsSecond(View view) {
        startActivity(new Intent(log_in.this,MainActivity.class));
    }
}
