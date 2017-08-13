package com.example.yousry.healthcareapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import AdapterClasses.HistoryArrayAdapter;
import BackgroundTaskFolder.BackgroundTask;
import DataProviderClasses.HistoryDataProvider;
import DataProviderClasses.ShowClinicsDataProvider;

public class HistoryActivity extends AppCompatActivity {

    private static final String URL_VIEW_HISTORY ="http://healthcareapp.16mb.com/healthcare/display_history.php";
    private static final int REQUEST_CODE =1;

    private int doctor_id, patient_id;

    BackgroundTask backgroundTask;
    String mUserType;
    TextView historyTextView;
    ListView historyListView;
    HistoryArrayAdapter historyArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyTextView = (TextView) findViewById(R.id.history_textView);
        historyListView = (ListView) findViewById(R.id.history_listView);


        historyArrayAdapter =new HistoryArrayAdapter(this,R.layout.row_history);



        SharedPreferences sharedPreferences=getSharedPreferences("User_Preference", Context.MODE_PRIVATE);
        mUserType = sharedPreferences.getString("type", "");

        if(mUserType.equals("DOCTOR"))
        {
            doctor_id = sharedPreferences.getInt("doctor_id",0);
            patient_id = getIntent().getIntExtra("user_id",0);
        }
        else
        {
            patient_id = sharedPreferences.getInt("patient_id",0);
            doctor_id = 0;//getIntent().getIntExtra("user_id",0); for adding by patient
        }

        historyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backgroundTask = new BackgroundTask(HistoryActivity.this);
                backgroundTask.setTag(BackgroundTask.TAGS.VIEW_HISTORY);
                backgroundTask.setParamsItem(String.valueOf(patient_id));
                backgroundTask.execute(URL_VIEW_HISTORY);
                backgroundTask.setListener(new BackgroundTask.MyAsyncListener() {
                    @Override
                    public void onSuccessfulExecute(String data) {
                        if(data != null){
                            try {
                                if(historyArrayAdapter.length()>0)
                                {
                                    historyArrayAdapter.clear();
                                    historyArrayAdapter.notifyDataSetChanged();
                                }
                                JSONArray jsonArray=new JSONArray(data);
                                for (int i=0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    HistoryDataProvider provider=new HistoryDataProvider
                                            (jsonObject.getString("doctor_name")
                                                    ,jsonObject.getString("case_description")
                                                    ,jsonObject.getString("drug")
                                                    ,jsonObject.getString("note")
                                                    ,jsonObject.getString("day")+" "+
                                                    jsonObject.getString("month")+" "+jsonObject.getString("year"));
                                    historyArrayAdapter.add(provider);
                                }




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


            }
        });
        historyListView.setAdapter(historyArrayAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(mUserType.equals("DOCTOR"))
            getMenuInflater().inflate(R.menu.doctor_history_menu, menu);
        else{
            getMenuInflater().inflate(R.menu.home_patient,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeDoctor/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.doctor_history_menu_action_settings) {
            Intent intent = new Intent(getApplicationContext(),CreateHistoryActivity.class).putExtra("patient_id", patient_id);

            startActivityForResult(intent,REQUEST_CODE);
            return true;
        }
        else if(id == R.id.doctor_history_menu_action_back) {
            Intent intent=new Intent(getApplicationContext(),SearchHomeDoctorActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK){
                patient_id=data.getIntExtra("patient_id", 0);
                historyArrayAdapter.clear();
                historyArrayAdapter.notifyDataSetChanged();
            }
        }
    }
}
