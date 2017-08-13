package com.example.yousry.healthcareapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import AppClasses.Clinic;
import AppClasses.Patient;

public class set_location extends FragmentActivity implements LocationListener {

    GoogleMap googleMap;
    double latitude;
    double longitude;
    LatLng latLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_patient_location_layout);
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else { // Google Play Services are available

            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();

            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);

            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
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
            Location location = locationManager.getLastKnownLocation(provider);

            if(location!=null){
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(provider, 20000, 0, (LocationListener) this);
        }
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                // when user want to define his place
                googleMap.clear();
                int counter = 0;
                Location loc = new Location(String.valueOf(counter + 1));
                loc.setLatitude(latLng.latitude);
                loc.setLongitude(latLng.longitude);
                add_Marker_byLocation(loc);
                Toast.makeText(getApplicationContext(),get_address_of_location(latLng),Toast.LENGTH_LONG).show();
            }
        });
        Button button= (Button) findViewById(R.id.filter_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(), "Latitude:" +  latitude  + ", Longitude:"+ longitude,Toast.LENGTH_LONG ).show();
                Intent intent =new Intent(getApplicationContext(),CreateClinicActivity.class);
                intent.putExtra("Latitude",latitude);
                intent.putExtra("Longitude",longitude);
                intent.putExtra("Address", get_address_of_location(latLng));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    // add marker on map by it's location
    private void add_Marker_byLocation(Location location) {
        // Getting latitude of the current location
        latitude = location.getLatitude();

        // Getting longitude of the current location
        longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        latLng = new LatLng(latitude, longitude);
        // Showing the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));


        googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Your Clinic are here ..."));
    }

    @Override
    public void onLocationChanged(Location location) {

/*
        // Getting latitude of the current location
        latitude = location.getLatitude();

        // Getting longitude of the current location
        longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        latLng = new LatLng(latitude, longitude);

        // Showing the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        // Setting latitude and longitude in the TextView tv_location
*/

    }
    private String get_address_of_location(LatLng latLng) {
        Geocoder geocoder;

        List<Address> addresses = new ArrayList<Address>();

        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            String Address = new String();

            Address = addresses.get(0).getAddressLine(0) + "-" + addresses.get(0).getAddressLine(1) + "-"
                    + addresses.get(0).getAddressLine(2) + "-" + addresses.get(0).getAddressLine(3);

            //Toast.makeText(getApplicationContext(),Address,Toast.LENGTH_SHORT).show();
            return Address;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;
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


}