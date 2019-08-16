package com.volvain.yash;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.volvain.yash.DAO.Database;

public class HelpSync extends AppCompatActivity implements LocationListener {
   private LocationManager locationManager;
    private String provider;
    private Location location;
    private int no=0;
    Long id;
    String name;
   protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       fetchPersonalDetails();
   locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
       Criteria criteria=new Criteria();
       provider=locationManager.getBestProvider(criteria,false);
       findLocationProvider();
           onLocationChanged(location);
       }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        sendRequest(no,id,name,longitude,latitude);
        no++;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        findLocationProvider();
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    private void findLocationProvider(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //TODO implement above check in caller activity
        location = locationManager.getLastKnownLocation(provider);
        Toast.makeText(this,""+location,Toast.LENGTH_LONG).show();
        if(location==null){
            provider=LocationManager.NETWORK_PROVIDER;
            location = locationManager.getLastKnownLocation(provider);

        }
    }
    private void sendRequest(int no,Long id,String name,Double longitude,Double Latitude){
       Data data=new Data.Builder()
               .putInt("no",no)
                .putLong("id",id)
                 .putString("name",name)
                .putDouble("longitude",longitude)
               .build();
        OneTimeWorkRequest work=new OneTimeWorkRequest.Builder(HelpReqServer.class)
                                .setInputData(data)
                                .build();
        WorkManager.getInstance().enqueue(work);
    }
    private void fetchPersonalDetails(){
       id=null;//TODO Fetch From Db
            String name="";
            Database db= new Database(this);
            id=db.getSenderId();
            name=db.getSenderName();
    }
}
