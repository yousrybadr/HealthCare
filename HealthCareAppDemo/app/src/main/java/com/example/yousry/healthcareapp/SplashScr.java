package com.example.yousry.healthcareapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import AppClasses.Doctor;
import AppClasses.Patient;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class SplashScr extends AppCompatActivity {

    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */


    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;
    private boolean mVisible;
    Doctor doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_scr);
       // Firebase.setAndroidContext(this);
        mVisible = true;

        mContentView = findViewById(R.id.imageView2);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        final Animation an= AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        final Animation an2= AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);
        mContentView.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mContentView.startAnimation(an2);
                finish();
                SharedPreferences sharedpreferences = getSharedPreferences("User_Preference", Context.MODE_PRIVATE);
                ;
                ;
                ;
                ;
                ;
                ;
                ;
                ;
                ;
                if(!sharedpreferences.contains("initialized")){
                    Intent i = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(i);
                }
                else
                {
                    if(sharedpreferences.getString("type","").equals("DOCTOR")) {
                        doctor=new Doctor();
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
                        Intent i= new Intent(getApplicationContext(),HomeDoctor.class);
                        i.putExtra("Doctor_obj", doctor);
                        startActivity(i);
                    }else{
                        Patient patient=new Patient();
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
                        Intent i= new Intent(getApplicationContext(),HomePatient.class);
                        i.putExtra("Patient_obj", patient);
                        startActivity(i);
                    }


                }

            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */


    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            getSupportActionBar().show();

        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    /**
     * Sclhedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
