package com.example.yousry.healthcareapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import BackgroundTaskFolder.BackgroundTask;

public class CreateClinicActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText descriptionEditText;
    EditText phoneEditText;
    EditText start_timeEditText;
    EditText end_timeEditText;
    TextView addressTextView;


    private final String URL_ADD_CLINIC = "http://healthcareapp.16mb.com/healthcare/add_clinic.php";
    public int doctor_id;
    double mLatitude;
    double mLongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_clinic);

        nameEditText = (EditText) findViewById(R.id.createClinic_editText_name);
        descriptionEditText = (EditText) findViewById(R.id.createClinic_editText_description);
        phoneEditText = (EditText) findViewById(R.id.createClinic_editText_phone);
        start_timeEditText = (EditText) findViewById(R.id.createClinic_editText_startTime);
        end_timeEditText = (EditText) findViewById(R.id.createClinic_editText_endTime);

        addressTextView = (TextView) findViewById(R.id.createClinic_textView_address);

        FloatingActionButton locationButton = (FloatingActionButton) findViewById(R.id.createClinic_fab_location);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), set_location.class);

                //intent.putExtra("flag", 1);

                startActivity(intent);

            }
        });

        Intent intent=getIntent();


        SharedPreferences sharedpreferences = getSharedPreferences("User_Preference", Context.MODE_PRIVATE);

        doctor_id = sharedpreferences.getInt("doctor_id",0);


        mLatitude = intent.getDoubleExtra("Latitude", 0);
        mLongitude = intent.getDoubleExtra("Longitude", 0);
        String mAddress = intent.getStringExtra("Address");

        if(mAddress!=null)
            addressTextView.setText(mAddress);

        Button button= (Button) findViewById(R.id.createClinic_button);

        ArrayList<Integer> workHours= new ArrayList<Integer>();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BackgroundTask backgroundTask=new BackgroundTask(CreateClinicActivity.this);
                backgroundTask.setParamsItem(String.valueOf(doctor_id));
                backgroundTask.setParamsItem(nameEditText.getText().toString());
                backgroundTask.setParamsItem(descriptionEditText.getText().toString());
                backgroundTask.setParamsItem(phoneEditText.getText().toString());
                backgroundTask.setParamsItem(start_timeEditText.getText().toString());
                backgroundTask.setParamsItem(end_timeEditText.getText().toString());
                backgroundTask.setParamsItem(String.valueOf(mLatitude));
                backgroundTask.setParamsItem(String.valueOf(mLongitude));
                backgroundTask.setParamsItem(addressTextView.getText().toString());
                backgroundTask.setTag(BackgroundTask.TAGS.CREATE_CLINIC);
                backgroundTask.execute(URL_ADD_CLINIC);
                backgroundTask.setListener(new BackgroundTask.MyAsyncListener() {
                    @Override
                    public void onSuccessfulExecute(String data) {
                        finish();
                    }
                });
            }
        });





    }


}
