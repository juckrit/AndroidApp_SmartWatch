package com.example.administrator.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    TextView myLat;
    TextView myLong;
    TextView myAccuracy;
    TextView myAltitude;
    TextView myAddress;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                myLat.setText(Double.toString(lastKnownLocation.getLatitude()) );
                myLong.setText(Double.toString(lastKnownLocation.getLongitude()) );
                myAccuracy.setText(Float.toString(lastKnownLocation.getAccuracy()));
                myAltitude.setText(Double.toString(lastKnownLocation.getAltitude()));
                Geocoder geocoder = new Geocoder(getApplicationContext(),Locale.getDefault());
                try {
                    //มีโอกาสที่ listAddress มีมากมายแต่เราไม่สนใจ เราสนใจแค่ 1 เรยใส่ 1 และมันมีโอกาสที่จะไม่มีเรย เรยต้องใส่ try catch
                    List<Address> listAddress  = geocoder.getFromLocation(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude(),1);
                    if(listAddress!=null&&listAddress.size()>0){
                        Log.i("Result LocationInfo",listAddress.get(0).toString());
                        String address = "";



                        if (listAddress.get(0).getSubThoroughfare()!=null){
                            address += listAddress.get(0).getSubThoroughfare() + " ";
                        }
                        if (listAddress.get(0).getThoroughfare()!=null){
                            address += listAddress.get(0).getThoroughfare()+ " ";
                        }
                        if (listAddress.get(0).getLocale()!=null){
                            address += listAddress.get(0).getLocality()+ " ";
                        }
                        if (listAddress.get(0).getCountryName()!=null){
                            address += listAddress.get(0).getCountryName()+ " ";
                        }

                        myAddress.setText(address + " " );

                        //Toast.makeText(getApplicationContext(),"Your post code is "+address,Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLat = (TextView)findViewById(R.id.myLat);
        myLong = (TextView)findViewById(R.id.myLong);
        myAccuracy = (TextView)findViewById(R.id.myAccuracy);
        myAltitude = (TextView)findViewById(R.id.myAltitude);
        myAddress = (TextView)findViewById(R.id.myAddress);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                myLat.setText(Double.toString(location.getLatitude()) );
                myLong.setText(Double.toString(location.getLongitude()) );
                myAccuracy.setText(Float.toString(location.getAccuracy()));
                myAltitude.setText(Double.toString(location.getAltitude()));
                Geocoder geocoder = new Geocoder(getApplicationContext(),Locale.getDefault());
                try {
                    //มีโอกาสที่ listAddress มีมากมายแต่เราไม่สนใจ เราสนใจแค่ 1 เรยใส่ 1 และมันมีโอกาสที่จะไม่มีเรย เรยต้องใส่ try catch
                    List<Address> listAddress  = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    if(listAddress!=null&&listAddress.size()>0){
                        Log.i("Result LocationInfo",listAddress.get(0).toString());
                        String address = "";



                        if (listAddress.get(0).getSubThoroughfare()!=null){
                            address += listAddress.get(0).getSubThoroughfare() + " ";
                        }
                        if (listAddress.get(0).getThoroughfare()!=null){
                            address += listAddress.get(0).getThoroughfare()+ " ";
                        }
                        if (listAddress.get(0).getLocale()!=null){
                            address += listAddress.get(0).getLocality()+ " ";
                        }
                        if (listAddress.get(0).getCountryName()!=null){
                            address += listAddress.get(0).getCountryName()+ " ";
                        }

                        myAddress.setText(address + " " );

                        //Toast.makeText(getApplicationContext(),"Your post code is "+address,Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }else {

            //check permission ก่อนเพราะว่า android mี่สูงกว่า 6.0 จะมีปัญหา
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //ในกรณีนี้ check ว่าถ้าไม่ได้รับ permission จะขอ permission ใหม่
                //ask for permission by popup
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                // already have permission then update location
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            }
        }

    }


}
