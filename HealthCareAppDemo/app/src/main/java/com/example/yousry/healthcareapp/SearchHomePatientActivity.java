package com.example.yousry.healthcareapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import AdapterClasses.GetDoctorsAdapters;
import AppClasses.Doctor;
import BackgroundTaskFolder.BackgroundTask;
import DataProviderClasses.GetDoctorsDataProvider;


public class SearchHomePatientActivity extends AppCompatActivity {

    private static final String URL_SEARCH_HOME_PATIENT ="http://healthcareapp.16mb.com/healthcare/Search_doctor_field.php";
    Spinner spinnerView;

    ListView doctorlistView;
    GetDoctorsAdapters adapter;
    BackgroundTask backgroundTask;
    ArrayList<Doctor> doctors;
    ArrayAdapter<CharSequence> adapterSpinner;
    int patient_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_home_patient);
        spinnerView= (Spinner) findViewById(R.id.spinner_fields);
        doctorlistView= (ListView) findViewById(R.id.fragment_search_home_patient_listView);

        doctors =new ArrayList<Doctor>();

        adapter =new GetDoctorsAdapters(this, R.layout.row_search_home_patient);

        adapterSpinner=ArrayAdapter.createFromResource(this, R.array.FieldsOfDoctors, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerView.setAdapter(adapterSpinner);
        spinnerView.setScrollContainer(true);
        spinnerView.setScrollContainer(true);

        Button button_ok = (Button) findViewById(R.id.button_ok);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();

                backgroundTask = new BackgroundTask(SearchHomePatientActivity.this);
                backgroundTask.setTag(BackgroundTask.TAGS.SEARCH_DOCTOR_FIELD);
                backgroundTask.setParamsItem(spinnerView.getSelectedItem().toString());
                backgroundTask.execute(URL_SEARCH_HOME_PATIENT);

                backgroundTask.setListener(new BackgroundTask.MyAsyncListener() {
                    @Override
                    public void onSuccessfulExecute(String data) {
                        if (data != null) {
                            try {
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Doctor doctor=new Doctor();
                                    doctor.setID(jsonObject.getInt("doctor_id"));
                                    doctor.setFirstName(jsonObject.getString("first_name"));
                                    doctor.setLastName(jsonObject.getString("last_name"));
                                    doctor.setUserName(jsonObject.getString("user_name"));
                                    doctor.setEmailAddress(jsonObject.getString("email"));
                                    doctor.setPhone(jsonObject.optString("phone"));
                                    doctor.setSex(jsonObject.getInt("sex"));
                                    doctor.setField(jsonObject.getString("field"));
                                    doctor.setImage(jsonObject.getString("image"));
                                    GetDoctorsDataProvider provider = new GetDoctorsDataProvider(
                                            jsonObject.getString("first_name") + " " + jsonObject.getString("last_name")
                                            , jsonObject.getString("email"));

                                    doctors.add(doctor);
                                    adapter.add(provider);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });


        doctorlistView.setAdapter(adapter);
        doctorlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPreferences=getSharedPreferences("User_Preference",Context.MODE_PRIVATE);
                patient_id =sharedPreferences.getInt("patient_id",0);
                Doctor doctor= doctors.get(position);
                Intent intent=new Intent(SearchHomePatientActivity.this,ProfileDoctorForPatientActivity.class);
                intent.putExtra("patient_id",patient_id);
                intent.putExtra("doctor_obj",doctor);
                startActivity(intent);

            }
        });



    }
}
