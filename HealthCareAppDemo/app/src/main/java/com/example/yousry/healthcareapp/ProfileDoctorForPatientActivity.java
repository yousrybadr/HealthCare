package com.example.yousry.healthcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import AdapterClasses.ShowClinicsBookingAdapter;
import AppClasses.Clinic;
import AppClasses.Doctor;
import BackgroundTaskFolder.BackgroundTask;

public class ProfileDoctorForPatientActivity extends AppCompatActivity {
    private static final String URL_GET_CLINICS="http://healthcareapp.16mb.com/healthcare/Get_Clinics.php";
    private static final String URL_ADD_USER_CHAT = "http://healthcareapp.16mb.com/healthcare/add_user_to_chat.php";
    ListView clinicListView;
    ShowClinicsBookingAdapter adapter;

    private BackgroundTask backgroundTask;
    TextView name_textView;
    TextView email_textView;
    TextView phone_textView;
    TextView sex_textView;
    TextView field_textView;
    TextView showClinics_textView;
    private int patient_id;
    private int doctor_id;
    Doctor doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_doctor_for_patient);
        clinicListView = (ListView) findViewById(R.id.profile_doctorforpatient_listView);
        adapter =new ShowClinicsBookingAdapter(this, R.layout.row_clinic_booking_layout);

        name_textView = (TextView) findViewById(R.id.profile_doctorforpatient_textView_name);
        email_textView = (TextView) findViewById(R.id.profile_doctorforpatient_textView_email);
        sex_textView = (TextView) findViewById(R.id.profile_doctorforpatient_textView_male);
        phone_textView = (TextView) findViewById(R.id.profile_doctorforpatient_textView_phone);
        field_textView = (TextView) findViewById(R.id.profile_doctorforpatient_textView_field);
        showClinics_textView = (TextView) findViewById(R.id.profile_doctorforpatient_textView_SHOWCLINICS);
        //ImageView imageView= (ImageView) findViewById(R.id.profile_doctorforpatient_imageView);


        Intent intent=getIntent();

        doctor = (Doctor) intent.getSerializableExtra("doctor_obj");
        patient_id=intent.getIntExtra("patient_id",0);
        name_textView.setText(doctor.getFirstName() +" "+doctor.getLastName());
        field_textView.setText(doctor.getField());
        email_textView.setText(doctor.getEmailAddress());
        phone_textView.setText(doctor.getPhone());
        sex_textView.setText(doctor.getSex() == 0 ? "Male" : "Female");


        doctor_id = doctor.getID();
        backgroundTask = new BackgroundTask(ProfileDoctorForPatientActivity.this);
        backgroundTask.setTag(BackgroundTask.TAGS.SHOW_CLINIC_ACTIVITY);
        backgroundTask.setParamsItem(String.valueOf(doctor_id));
        backgroundTask.execute(URL_GET_CLINICS);
        backgroundTask.setListener(new BackgroundTask.MyAsyncListener() {
            @Override
            public void onSuccessfulExecute(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Clinic provider = new Clinic
                                (jsonObject.getInt("clinic_id"),jsonObject.getString("name")
                                        , jsonObject.getString("description")
                                        , jsonObject.getString("phone")
                                        , jsonObject.getString("start_time")
                                        , jsonObject.getString("end_time")
                                        , jsonObject.getDouble("lat")
                                        , jsonObject.getDouble("lng")
                                        , jsonObject.getString("address")
                                        , patient_id);
                        adapter.add(provider);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //Bitmap bitmap = MainFunctions.StringToBitMap(doctor.getImage());
        //imageView.setImageBitmap(bitmap);
        showClinics_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), String.valueOf(patient_id), Toast.LENGTH_LONG).show();

                clinicListView.setAdapter(adapter);

                clinicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });


            }
        });
        final ImageButton imageButton= (ImageButton) findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton.setVisibility(View.INVISIBLE);
                backgroundTask =new BackgroundTask(ProfileDoctorForPatientActivity.this);
                backgroundTask.setTag(BackgroundTask.TAGS.ADD_USER_CHAT);
                backgroundTask.setParamsItem(String.valueOf(patient_id));
                backgroundTask.setParamsItem(String.valueOf(doctor_id));
                backgroundTask.execute(URL_ADD_USER_CHAT);
                backgroundTask.setListener(new BackgroundTask.MyAsyncListener() {
                    @Override
                    public void onSuccessfulExecute(String data) {
                        //TODO Access on data for Checking Do you make insertion or not !!
                        if(data.isEmpty() || data == null) {
                            // NO INSERTION
                            Toast.makeText(ProfileDoctorForPatientActivity.this,"There is Error .. Try again ,please",Toast.LENGTH_LONG).show();
                        }else{
                            // INSERTION IS EXIST
                            Toast.makeText(ProfileDoctorForPatientActivity.this,"Done",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });




    }
}
