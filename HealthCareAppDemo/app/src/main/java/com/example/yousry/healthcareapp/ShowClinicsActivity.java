package com.example.yousry.healthcareapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import AdapterClasses.ShowClinicsAdapter;
import BackgroundTaskFolder.BackgroundTask;
import DataProviderClasses.ShowClinicsDataProvider;

public class ShowClinicsActivity extends AppCompatActivity {

    private static final String URL_GET_CLINICS="http://healthcareapp.16mb.com/healthcare/Get_Clinics.php";
    ListView clinicListView;
    ShowClinicsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_clinics);
        clinicListView = (ListView) findViewById(R.id.show_clinic_listView);
        adapter =new ShowClinicsAdapter(this,R.layout.row_show_clinics_layout);
        SharedPreferences sharedpreferences = getSharedPreferences("User_Preference", Context.MODE_PRIVATE);

        int doctor_id =sharedpreferences.getInt("doctor_id",0);
        BackgroundTask backgroundTask=new BackgroundTask(getApplicationContext());
        backgroundTask.setTag(BackgroundTask.TAGS.GET_CLINICS);
        backgroundTask.setParamsItem(String.valueOf(doctor_id));
        backgroundTask.execute(URL_GET_CLINICS);
        backgroundTask.setListener(new BackgroundTask.MyAsyncListener() {
            @Override
            public void onSuccessfulExecute(String data) {
                if (data == null) {
                    Toast.makeText(getApplicationContext(),"No Data Found , please insert new clinic in your Home",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ShowClinicsDataProvider provider = new ShowClinicsDataProvider
                                (jsonObject.getString("name")
                                        , jsonObject.getString("description")
                                        , jsonObject.getString("phone")
                                        , jsonObject.getString("start_time")
                                        , jsonObject.getString("end_time")
                                        , jsonObject.getString("address"));
                        adapter.add(provider);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        clinicListView.setAdapter(adapter);

    }
}
