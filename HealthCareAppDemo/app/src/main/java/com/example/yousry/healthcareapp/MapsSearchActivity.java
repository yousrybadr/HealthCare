package com.example.yousry.healthcareapp;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import AppClasses.Clinic;
import AppClasses.Doctor;
import BackgroundTaskFolder.BackgroundTask;
import DataProviderClasses.GetDoctorsDataProvider;

public class MapsSearchActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    private static final String URL_SEARCH_MAP_PATIENT = "http://healthcareapp.16mb.com/healthcare/search_clinics_field.php";
    private final String TAG = MapsSearchActivity.class.getSimpleName();
    private GoogleMap googleMap;
    Spinner field_Spinner;
    //LocationListener locationListener;
    private Location myLoc;
    private ArrayList<Location> locations = new ArrayList<Location>();
    private GoogleApiClient client;
    ArrayAdapter<CharSequence> adapter;
    Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);
        field_Spinner = (Spinner) findViewById(R.id.map_spinner);
        filterButton = (Button) findViewById(R.id.filter_btn);

        adapter = ArrayAdapter.createFromResource(this, R.array.FieldsOfDoctors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        field_Spinner.setAdapter(adapter);
        field_Spinner.setScrollContainer(true);
        field_Spinner.setScrollContainer(true);
        //SetUpMapIfNeeded();
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else { // Google Play Services are available

            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            fm.getMapAsync(this);

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
            myLoc = locationManager.getLastKnownLocation(provider);
            //myLoc = get_my_location();
            if (myLoc != null) {
                onLocationChanged(myLoc);
            }
            locationManager.requestLocationUpdates(provider, 20000, 0, (LocationListener) this);
        }


        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //filterButton.setVisibility(View.INVISIBLE);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locations.size() > 0)
                    getNearestDoctor();
                else {
                    filterButton.setVisibility(View.INVISIBLE);
                    Toast.makeText(MapsSearchActivity.this, "Select item from Spinner fisrt", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




   /* private Location get_my_location() {
        googleMap.setMyLocationEnabled(true);


        // that control and give el permission
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        // object from provider
        Criteria criteria = new Criteria();

        // get best provider
        String Provider = locationManager.getBestProvider(criteria, true);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return get_my_location();
        }
        final Location mylocation = locationManager.getLastKnownLocation(Provider);
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(mylocation.getLatitude(), mylocation.getLongitude())).title("Me"));

        return mylocation;
    }*/

    // bzbt el camera 3la asas el latlang ely hwa el location y3ne
    // note : e5tlft el mosmyaat bs el natega wa7da
    /*private void organize_camera(LatLng latLng) {

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }*/

    // add marker on map by it's location
    private void add_Marker_byLocation(Location location) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("You are here "));
    }


    // get address of locatioon that be selcted on map
    private String get_address_of_location(LatLng latLng) {
        try {
            Geocoder geocoder;

            List<Address> addresses = new ArrayList<Address>();

            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            myLoc.setLatitude(latLng.latitude);
            myLoc.setLongitude(latLng.longitude);

            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            String Address = new String();

            Address = addresses.get(0).getAddressLine(0) + "-" + addresses.get(0).getAddressLine(1) + "-"
                    + addresses.get(0).getAddressLine(2) + "-" + addresses.get(0).getAddressLine(3);
            return Address;
            // Toast.makeText(getApplicationContext(), Address, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }



    public void getNearestDoctor() {
        // clear all markers that on map
        googleMap.clear();

        if (myLoc == null) {
            Toast.makeText(MapsSearchActivity.this, "No Location for you", Toast.LENGTH_SHORT).show();
            return;
        }
        //---> get all distences from location list
        ArrayList<Float> distenceList = new ArrayList<Float>();
        for (int i = 0; i < locations.size(); i++) {
            distenceList.add(myLoc.distanceTo(locations.get(i)));
        }
        Float min = Float.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < distenceList.size(); i++) {
            if (min > distenceList.get(i)) {
                min = distenceList.get(i);
                index = i;
            }
        }


        // add marker to user location
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(myLoc.getLatitude(), myLoc.getLongitude())).title("Me"));

        Resources res = getResources();
        int id = R.mipmap.ic_location_marker_pin_map_gps;
        Bitmap b = BitmapFactory.decodeResource(res, id);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(b);
        final LatLng latLng = new LatLng(locations.get(index).getLatitude(), locations.get(index).getLongitude());
        // add marker to the nearest doctor location
        MarkerOptions marker = new MarkerOptions().position(latLng).title("nearest doctor ").icon(bitmapDescriptor).snippet(get_address_of_location(latLng));
        googleMap.addMarker(marker);

        // clear all locations
        locations.clear();
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;
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
        googleMap.setMyLocationEnabled(true);
        myLoc =googleMap.getMyLocation();
       // Log.d(TAG,String.valueOf(myLoc.getLatitude()));
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Google_Map Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.yousry.healthcareapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Google_Map Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.yousry.healthcareapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }



    // search about doctor on map if you want
    public void search_doctor(View view) {
        filterButton.setVisibility(View.VISIBLE);
        locations.clear();
        //googleMap.clear();
        Log.d(TAG,field_Spinner.getSelectedItem().toString());
        BackgroundTask backgroundTask=new BackgroundTask(MapsSearchActivity.this);
        backgroundTask.setTag(BackgroundTask.TAGS.SEARCH_CLINIC_FIELD);
        backgroundTask.setParamsItem(field_Spinner.getSelectedItem().toString());
        backgroundTask.execute(URL_SEARCH_MAP_PATIENT);
        backgroundTask.setListener(new BackgroundTask.MyAsyncListener() {
            @Override
            public void onSuccessfulExecute(String data) {
                if(data == null) {
                    Log.d(TAG, "null");
                }else if (data != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(data);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Clinic clinic =new Clinic();
                            clinic.setLatitude(jsonObject.getDouble("lat"));
                            clinic.setLongitude(jsonObject.getDouble("lng"));
                            int counter = 0;
                            Location loc = new Location(String.valueOf(counter + 1));
                            loc.setLatitude(clinic.getLatitude());
                            loc.setLongitude(clinic.getLongitude());
                            locations.add(loc);
                            add_Marker_byLocation(loc);
                            Log.d(TAG,"Latitude = "+loc.getLatitude() +" , Longitude = "+loc.getLongitude());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"No Data found",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        myLoc=location;


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
