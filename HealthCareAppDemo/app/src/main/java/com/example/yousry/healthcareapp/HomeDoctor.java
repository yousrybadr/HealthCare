package com.example.yousry.healthcareapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.Toast;

import AppClasses.Doctor;

public class HomeDoctor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fr =new Fragment();


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Doctor doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_doctor);
        fragmentManager=getFragmentManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Intent i = getIntent();

        //doctor =(Doctor)i.getSerializableExtra("Doctor_obj");

        doctor=new Doctor();
        SharedPreferences sharedpreferences = getSharedPreferences("User_Preference", Context.MODE_PRIVATE);
        doctor.setID(sharedpreferences.getInt("doctor_id",0));
        doctor.setFirstName(sharedpreferences.getString("first_name", "empty"));
        doctor.setLastName(sharedpreferences.getString("last_name", "empty"));
        doctor.setUserName(sharedpreferences.getString("user_name", "empty"));
        doctor.setPassword(sharedpreferences.getString("password", "empty"));
        doctor.setEmailAddress(sharedpreferences.getString("email", "empty"));
        doctor.setSex(sharedpreferences.getInt("sex", 0));
        doctor.setField(sharedpreferences.getString("field", "empty"));
        doctor.setPhone(sharedpreferences.getString("phone", "empty"));
        doctor.setImage(sharedpreferences.getString("image", "empty"));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_doctor);
        View hView =  navigationView.getHeaderView(0);
        TextView nameOfNavigationDrawer = (TextView)hView.findViewById(R.id.nav_nameOfDoctorHome);
        nameOfNavigationDrawer.setText(doctor.getFirstName() + " " + doctor.getLastName());
        TextView fieldOfNavigationDrawer = (TextView)hView.findViewById(R.id.nav_fieldOfDoctorHome);
        fieldOfNavigationDrawer.setText(doctor.getField());
        TextView emailOfNavigationDrawer = (TextView)hView.findViewById(R.id.nav_emailOfDoctorHome);
        emailOfNavigationDrawer.setText(doctor.getEmailAddress());

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
        getMenuInflater().inflate(R.menu.home_doctor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeDoctor/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout_setting) {
            Intent intent;
            SharedPreferences sharedPreferences=getSharedPreferences("User_Preference", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Exit me", true);
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
            case R.id.nav_profile_doctor:
                fragmentTransaction=fragmentManager.beginTransaction();
                fr = new ProfileDoctorFragment();
                fragmentTransaction.add(R.id.fragmentDoctor, fr);
                fragmentTransaction.commit();
                break;
            case R.id.nav_search_doctor:
                intent=new Intent(getApplicationContext(),SearchHomeDoctorActivity.class);
                intent.putExtra("doctor_id",doctor.getID());
               // Toast.makeText(getApplicationContext(),doctor.getID()+"",Toast.LENGTH_LONG).show();
                startActivity(intent);
                break;
            case R.id.nav_clinics_doctor:
                intent=new Intent(getApplicationContext(),ShowClinicsActivity.class);
                //Toast.makeText(getApplicationContext(),doctor.getID()+"",Toast.LENGTH_LONG).show();
                startActivity(intent);
                break;
            case R.id.nav_chat_doctor:
                startActivity(new Intent(HomeDoctor.this,RoomActivity.class));
                break;
            case R.id.nav_logout_doctor:
                SharedPreferences sharedPreferences=getSharedPreferences("User_Preference", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Exit me", true);
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
