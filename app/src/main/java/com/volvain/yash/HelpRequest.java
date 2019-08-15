package com.volvain.yash;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.widget.TextView;

public class HelpRequest extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(this);
        loadFragment(new homeFragment());


    }

    private boolean loadFragment(Fragment fragment){
        if (fragment !=null){
getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment =null;
        switch (menuItem.getItemId()){
            case R.id.navigation_home:
                fragment= new homeFragment();
                break;
            case  R.id.navigation_dashboard:
                fragment=new settingsFragment();
                break;
            case R.id.navigation_notifications:
                fragment=new notificationsFragment();
                break;
            case  R.id.login:
                fragment=new loginFragment();
                break;
        }
        return loadFragment(fragment);
    }

}
