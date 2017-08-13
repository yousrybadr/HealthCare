package com.example.yousry.healthcareapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import AppClasses.Patient;

public class HomePatient extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fr =new Fragment();


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;



    Patient patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_patient);
        fragmentManager = getFragmentManager();
      

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        patient=new Patient();
        SharedPreferences sharedpreferences = getSharedPreferences("User_Preference", Context.MODE_PRIVATE);
        patient.setID(sharedpreferences.getInt("patient_id",0));
        patient.setFirstName(sharedpreferences.getString("first_name", "empty"));
        patient.setLastName(sharedpreferences.getString("last_name", "empty"));
        patient.setUserName(sharedpreferences.getString("user_name", "empty"));
        patient.setPassword(sharedpreferences.getString("password", "empty"));
        patient.setEmailAddress(sharedpreferences.getString("email", "empty"));
        patient.setSex(sharedpreferences.getInt("sex", 0));
        patient.setJob(sharedpreferences.getString("job", "empty"));
        patient.setPhone(sharedpreferences.getString("phone", "empty"));
        patient.setImage(sharedpreferences.getString("image", "empty"));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_patient);
        View hView =  navigationView.getHeaderView(0);
        TextView nameOfNavigationDrawer = (TextView)hView.findViewById(R.id.nav_nameOfPatientHome);
        nameOfNavigationDrawer.setText(patient.getFirstName() + " " + patient.getLastName());
        TextView fieldOfNavigationDrawer = (TextView)hView.findViewById(R.id.nav_emailOfPatientHome);
        fieldOfNavigationDrawer.setText(patient.getEmailAddress());
        TextView emailOfNavigationDrawer = (TextView)hView.findViewById(R.id.nav_phoneOfPatientHome);
        emailOfNavigationDrawer.setText(patient.getPhone());

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(HomePatient.this,SettingsPatientActivity.class));
            return true;
        }
        else if(id ==R.id.logout_setting)
        {
            Intent intent;
            SharedPreferences sharedPreferences=getSharedPreferences("User_Preference", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Exit me", true);
            intent.putExtra("user_name",patient.getUserName());
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.nav_profile_patient:
                fragmentTransaction=fragmentManager.beginTransaction();
                fr = new ProfilePatientFragment();
                fragmentTransaction.add(R.id.fragmentPatient, fr);
                fragmentTransaction.commit();
                break;
            case R.id.nav_search_patient:
                startActivity(new Intent(getApplicationContext(),SearchHomePatientActivity.class));
                break;
            case R.id.nav_map_patient:
                startActivity(new Intent(getApplicationContext(), MapsSearchActivity.class));
                break;
            case R.id.nav_chat_patient:

                startActivity(new Intent(HomePatient.this,RoomActivity.class));
                break;
            case R.id.nav_pay_patient:
                Intent intentPay = new Intent(Intent.ACTION_MAIN);

                intentPay.setComponent(new ComponentName("com.example.dell.paypal_mpl_demo", "com.example.dell.paypal_mpl_demo.PayPal_selection"));
                startActivity(intentPay);
                break;
            case R.id.nav_expertsys_patient:
                 startActivity(new Intent(getApplicationContext(),Medical_Diagnosis_Activity.class));

                break;
            case R.id.nav_logout_patient:
                SharedPreferences sharedPreferences=getSharedPreferences("User_Preference", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Exit me", true);
                intent.putExtra("user_name",patient.getUserName());
                startActivity(intent);
                finish();

                break;
            default:
                break;}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
