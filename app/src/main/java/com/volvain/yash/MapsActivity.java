package com.volvain.yash;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.volvain.yash.DAO.Database;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , LocationListener {
    private GoogleMap mMap;
    private LocationManager locationManager;
    private String provider;
    private Location location;
    private double myLatitude;
    private double myLongitude;
    private double reqesterLongitude;
    private double requesterLatitude;
    private Handler mHandler=new Handler();
    public int AAAA=0;
    private Long id;
    Database db;

    private static final float DEFAULT_ZOOM = 15;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //myLatitude=32.0954731;
        //myLongitude=76.3923159;
        Log.i("gauravrmsc","a2");
        super.onCreate(savedInstanceState);
        Log.i("gauravrmsc","a3");
        setContentView(R.layout.activity_maps2);
        db=new Database(MapsActivity.this);
        Log.i("gauravrmsc","a4");
        //id=Long.parseLong(this.getIntent().getStringExtra("id"));
        id=8628087622l;
        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            findLocationProvider();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        DirectionsResult result=directionsResult();
        Log.i("gauravrmsc","result="+result);
        addMarkersToMap(result,mMap);
        addPolyline(result,mMap);
        onLocationChanged(location);
      //  mRunnable.run();TODO

        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    @Override
    public void onLocationChanged(Location location) {
        //myLongitude=location.getLongitude();
        //myLatitude=location.getLatitude();
        myLatitude=32.0954731;
        myLongitude=76.3923159;
        DirectionsResult result=directionsResult();
        addMarkersToMap(result,mMap);
        addPolyline(result,mMap);
    }
/*
    private Runnable mRunnable=new Runnable(){

        @Override
        public void run() {
           /* Long tempLatitude= db.getHelpLat(id);
            Long tempLongitude= db.getHelpLng(id);
            if(!tempLatitude.equals(reqesterLongitude)||!tempLongitude.equals(reqesterLongitude)){
                reqesterLongitude=tempLongitude;
                requesterLatitude=tempLatitude;
                DirectionsResult result=directionsResult();
                addMarkersToMap(result,mMap);//TODO
                addPolyline(result,mMap);
            }

            mHandler.postDelayed(this,10000);
        }
    };*/
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            findLocationProvider();

        }
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            findLocationProvider();
        }
    }
    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3)
                .setApiKey("AIzaSyAp6rVERlPFzV8BRn31jn6lqmrGBOmj58I")
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }
    private DirectionsResult directionsResult ()  {
        @NonNull
        DirectionsResult result = null;
        try {
            result = DirectionsApi.
                    newRequest(getGeoContext())
                    .mode(TravelMode.DRIVING).origin(""+myLatitude+","+myLongitude)
                    .destination(""+requesterLatitude+","+reqesterLongitude)
                  //  .mode(TravelMode.DRIVING).origin("32.095190,76.389376")
                    //.destination("32.0954031,76.3923159")
                    .await();
        }  catch (InterruptedException e) {
            e.printStackTrace();
            Log.i("gauravrmsc","Api"+e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("gauravrmsc","Api"+e);
        } catch (com.google.maps.errors.ApiException e) {
            e.printStackTrace();
            Log.i("gauravrmsc","Api"+e);Log.i("gauravrmsc","Api"+e);
        }

        return result;
    }
    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {
        if(results.routes.length>0){
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0].startLocation.lat,results.routes[0].legs[0].startLocation.lng)).title(results.routes[0].legs[0].startAddress));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude,myLongitude),15));
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0].endLocation.lat,results.routes[0].legs[0].endLocation.lng)).title(results.routes[0].legs[0].endAddress).snippet(getEndLocationTitle(results)));}
        else{
            mMap.addMarker(new MarkerOptions().position(new LatLng(myLatitude+0.000009,myLongitude)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude,myLongitude),20));
            mMap.addMarker(new MarkerOptions().position(new LatLng(myLatitude,myLongitude)).snippet(getEndLocationTitle(results)));
        }

    }
    private String getEndLocationTitle(DirectionsResult results){      if(results.routes.length>0)  return  "Time :"+ results.routes[0].legs[0].duration.humanReadable + " Distance :" + results.routes[0].legs[0].distance.humanReadable;   return ""; }
    private void addPolyline(DirectionsResult results, GoogleMap mMap) { if(results.routes.length>0){      List<LatLng> decodedPath = PolyUtil.decode(results.routes[0].overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));}
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void findLocationProvider(){
        Log.i("gauravrmsc","Finding Provider");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Location Premission Required",Toast.LENGTH_LONG);
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},this.AAAA);
        }
        //TODO implement above check in caller activity

        Criteria criteria=new Criteria();
        provider=locationManager.getBestProvider(criteria,false);
        location = locationManager.getLastKnownLocation(provider);
        Toast.makeText(this,""+location,Toast.LENGTH_LONG).show();
        if(location==null){
            Log.i("gauravrmsc","gps not available");
            provider=LocationManager.NETWORK_PROVIDER;
            location = locationManager.getLastKnownLocation(provider);

        }
        Log.i("gauravrmsc","Location"+location);
    }
}
