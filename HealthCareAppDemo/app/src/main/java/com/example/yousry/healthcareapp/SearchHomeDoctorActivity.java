package com.example.yousry.healthcareapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import AdapterClasses.GetDoctorsAdapters;
import AppClasses.Doctor;
import AppClasses.Patient;
import BackgroundTaskFolder.BackgroundTask;
import DataProviderClasses.GetDoctorsDataProvider;


public class SearchHomeDoctorActivity extends AppCompatActivity {
    private static final String URL_ADD_USER_CHAT = "http://healthcareapp.16mb.com/healthcare/add_user_to_chat.php";
    private static final String URL_SEARCH_HOME_DOCTOR ="http://healthcareapp.16mb.com/healthcare/Search_patient_username.php";
    SearchView searchView;
    BackgroundTask backgroundTask;
    ArrayList<Doctor> doctors;
    Patient patient=new Patient();
    ViewSwitcher viewSwitcher;
    boolean mChecking =false;
    int doctor_id;
    ImageButton imageButton;

    TextView nameTextView,userNameTextView,jobTextView,sexTextView,emailTextView,phoneTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_home_doctor);

        searchView= (SearchView) findViewById(R.id.search_home_doctor_searchView);
        viewSwitcher = (ViewSwitcher) findViewById(R.id.searcch_home_doctor_viewSwitcher);
        nameTextView=(TextView)findViewById(R.id.search_home_doctor_textView_name);
        userNameTextView=(TextView)findViewById(R.id.search_home_doctor_textView_userName);
        jobTextView=(TextView)findViewById(R.id.search_home_doctor_textView_job);
        sexTextView=(TextView)findViewById(R.id.search_home_doctor_textView_sex);
        emailTextView=(TextView)findViewById(R.id.search_home_doctor_textView_email);
        phoneTextView=(TextView)findViewById(R.id.search_home_doctor_textView_phone);
        viewSwitcher.setDisplayedChild(1);
        Button viewHistory = (Button) findViewById(R.id.searcch_home_doctor_button_history);
        SharedPreferences sharedpreferences = getSharedPreferences("User_Preference", Context.MODE_PRIVATE);
        doctor_id =sharedpreferences.getInt("doctor_id",0);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mChecking = false;
                backgroundTask = new BackgroundTask(SearchHomeDoctorActivity.this);
                backgroundTask.setTag(BackgroundTask.TAGS.SEARCH_PATIENT);
                backgroundTask.setParamsItem(query); //userName
                backgroundTask.execute(URL_SEARCH_HOME_DOCTOR);

                backgroundTask.setListener(new BackgroundTask.MyAsyncListener() {
                    @Override
                    public void onSuccessfulExecute(String data) {
                        if (data != null) {


                            try {
                                mChecking = false;
                                JSONArray jsonArray = new JSONArray(data);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                patient.setID(jsonObject.getInt("patient_id"));
                                patient.setFirstName(jsonObject.getString("first_name"));
                                patient.setLastName(jsonObject.getString("last_name"));
                                patient.setUserName(jsonObject.getString("user_name"));
                                patient.setEmailAddress(jsonObject.getString("email"));
                                patient.setPhone(jsonObject.optString("phone"));
                                patient.setSex(jsonObject.getInt("sex"));
                                patient.setJob(jsonObject.getString("job"));
                                patient.setImage(jsonObject.getString("image"));
                                viewSwitcher.setDisplayedChild(0);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                            viewSwitcher.setDisplayedChild(1);
                            mChecking =true;
                        }
                    }
                });
                nameTextView.setText(patient.getFirstName() + " " + patient.getLastName());
                userNameTextView.setText(patient.getUserName());
                jobTextView.setText(patient.getJob());
                sexTextView.setText(patient.getSex() == 0 ? "male" : "female");
                emailTextView.setText(patient.getEmailAddress());
                phoneTextView.setText(patient.getPhone());
                Toast.makeText(getApplicationContext(),patient.getEmailAddress(),Toast.LENGTH_LONG).show();


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    viewSwitcher.setDisplayedChild(1);
                    mChecking = true;
                }
                return false;
            }
        });


        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class).putExtra("user_id", patient.getID()));
            }
        });

        imageButton = (ImageButton) findViewById(R.id.search_home_doctor_imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton.setVisibility(View.INVISIBLE);
                backgroundTask =new BackgroundTask(SearchHomeDoctorActivity.this);
                backgroundTask.setTag(BackgroundTask.TAGS.ADD_USER_CHAT);
                backgroundTask.setParamsItem(String.valueOf(patient.getID()));
                backgroundTask.setParamsItem(String.valueOf(doctor_id));
                backgroundTask.execute(URL_ADD_USER_CHAT);
                backgroundTask.setListener(new BackgroundTask.MyAsyncListener() {
                    @Override
                    public void onSuccessfulExecute(String data) {
                        //TODO Access on data for Checking Do you make insertion or not !!
                        if(data.isEmpty() || data == null) {
                            // NO INSERTION
                            Toast.makeText(SearchHomeDoctorActivity.this,"There is Error .. Try again ,please",Toast.LENGTH_LONG).show();
                        }else{
                            // INSERTION IS EXIST
                            Toast.makeText(SearchHomeDoctorActivity.this,"Done",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


    }
}
