package com.example.yousry.healthcareapp;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import AdapterClasses.RoomAdapter;
import BackgroundTaskFolder.BackgroundTask;
import DataProviderClasses.RoomDataProvider;

public class RoomActivity extends AppCompatActivity {
    private static final String URL_SHOW_ROOM_PATIENT = "http://healthcareapp.16mb.com/healthcare/show_rooms_patient.php";
    private static final String URL_SHOW_ROOM_DOCTOR = "http://healthcareapp.16mb.com/healthcare/show_room_doctor.php";
    private final String TAG = RoomActivity.class.getSimpleName();
    ListView listView;
    RoomAdapter adapter;
    ImageView imageView;
    BackgroundTask backgroundTask;
    String mUserType;
    int patient_id;
    int doctor_id;
    ArrayList<String> userNameList;
    ArrayList<Integer> chatIdList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getType();
        imageView = (ImageView) findViewById(R.id.room_imageView_refresh);
        listView= (ListView) findViewById(R.id.room_listView);
        adapter=new RoomAdapter(this,R.layout.row_room);
        userNameList =new ArrayList<String>();
        chatIdList =new ArrayList<Integer>();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                backgroundTask=new BackgroundTask(RoomActivity.this);
                backgroundTask.setTag(BackgroundTask.TAGS.SHOW_ROOMS);
                if(mUserType.equals("PATIENT"))
                {
                    getPatientID();
                    backgroundTask.setParamsItem(String.valueOf(patient_id));
                    backgroundTask.execute(URL_SHOW_ROOM_PATIENT);
                }else{
                    getDoctorID();
                    backgroundTask.setParamsItem(String.valueOf(doctor_id));
                    backgroundTask.execute(URL_SHOW_ROOM_DOCTOR);
                }
                backgroundTask.setListener(new BackgroundTask.MyAsyncListener() {
                    @Override
                    public void onSuccessfulExecute(String data) {
                        try {
                            Log.d(TAG, data);
                            JSONArray jsonArray = new JSONArray(data);
                            for (int i = 0; i < jsonArray.length(); ++i) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                RoomDataProvider roomDataProvider = new RoomDataProvider(
                                        jsonObject.getString("first_name") + " " + jsonObject.getString("last_name"),
                                        jsonObject.getString("email"),
                                        jsonObject.getString("phone")
                                );

                                roomDataProvider.chat_id =jsonObject.getInt("chat_id");
                                userNameList.add(jsonObject.getString("user_name"));
                                chatIdList.add(jsonObject.getInt("chat_id"));
                                adapter.add(roomDataProvider);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*PackageManager p = getPackageManager();
                ComponentName componentName = new ComponentName("com.firebase.androidchat", "com.firebase.androidchat.MainActivity");
                p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                */Intent intentChat = new Intent(Intent.ACTION_MAIN);
                RoomDataProvider roomDataProvider=(RoomDataProvider) adapter.getItem(position);
                intentChat.putExtra("User1", getUserName());
                intentChat.putExtra("User2", userNameList.get(position) );
                intentChat.putExtra("chat_id", roomDataProvider.chat_id);
                intentChat.setComponent(new ComponentName("com.firebase.androidchat", "com.firebase.androidchat.MainActivity"));
                startActivity(intentChat);
            }
        });

    }
    private void getType() {
        SharedPreferences sharedPreferences=getSharedPreferences("User_Preference",0);
        mUserType = sharedPreferences.getString("type",null);
    }
    private void getPatientID() {
        SharedPreferences sharedPreferences=getSharedPreferences("User_Preference",0);
        patient_id = sharedPreferences.getInt("patient_id",0);
    }
    private void getDoctorID() {
        SharedPreferences sharedPreferences=getSharedPreferences("User_Preference",0);
        doctor_id = sharedPreferences.getInt("doctor_id",0);
    }

    private String getUserName() {
        SharedPreferences sharedPreferences=getSharedPreferences("User_Preference",0);
        return  sharedPreferences.getString("user_name", "default");
    }

}
