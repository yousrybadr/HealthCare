package com.example.yousry.healthcareapp;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ViewAppointmentActivity extends AppCompatActivity {

    private static final String URL_VIEW_APPOINTMENT = "http://healthcareapp.16mb.com/healthcare/view_appointment.php";
    private static final String TAG = ViewAppointmentActivity.class.getSimpleName();

    TextView dateTextView;
    TextView timeTextView;
    TextView nameTextView;
    TextView descriptionTextView;
    TextView phoneTextView;
    TextView toTextView;
    TextView fromTextView;
    TextView addressTextView;

    String appointment_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);
        dateTextView = (TextView) findViewById(R.id.view_appointment_textView_date);
        timeTextView = (TextView) findViewById(R.id.view_appointment_textView_time);
        nameTextView = (TextView) findViewById(R.id.view_appointment_textView_name);
        descriptionTextView = (TextView) findViewById(R.id.view_appointment_textView_description);
        phoneTextView = (TextView) findViewById(R.id.view_appointment_textView_phone);
        toTextView = (TextView) findViewById(R.id.view_appointment_textView_To);
        fromTextView = (TextView) findViewById(R.id.view_appointment_textView_from);
        addressTextView = (TextView) findViewById(R.id.view_appointment_textView_address);


        if(getIntent().hasExtra("appId")) {
            appointment_id =getIntent().getStringExtra("appId");
            Log.d(TAG,"true // " +appointment_id);
        }//else{appointment_id="2"; Log.d(TAG,"false .. "+appointment_id);}
        Log.d(TAG, "Task will start , appointment_id = " + appointment_id);
        //Toast.makeText(getApplicationContext(), appointment_id,Toast.LENGTH_LONG).show();
        RunOnBackGround(URL_VIEW_APPOINTMENT, appointment_id);




    }



    //Connection

    public void RunOnBackGround(String url,String appointment_id)  {
        final Handler handler=new Handler(Looper.getMainLooper());
        final String[] jsonData = new String[1];
        final OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormEncodingBuilder()
                .add("appointment_id", appointment_id)
                .build();;


        Request request = new Request.Builder().url(url).post(body).build();
        final Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("Eff", "Registration error: " + e.getMessage());
                System.out.println("Registration Error" + e.getMessage());
            }

            @Override
            public void onResponse(Response response) {
                try {
                    jsonData[0] = response.body().string();

                    if(response.isSuccessful()) {
                        Log.d(TAG, jsonData[0]);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                JSONArray jsonArray;
                                try {
                                    jsonArray = new JSONArray(jsonData[0]);
                                    JSONObject jsonObject=jsonArray.getJSONObject(0) ;
                                    dateTextView.setText(jsonObject.getString("day") + " / " + jsonObject.getString("month")+" / "+jsonObject.getString("year"));
                                    timeTextView.setText(jsonObject.getString("hour")+" : "+jsonObject.getString("minute"));
                                    nameTextView.setText(jsonObject.getString("name"));
                                    descriptionTextView.setText("description");
                                    phoneTextView.setText(jsonObject.getString("phone"));
                                    fromTextView.setText(jsonObject.getString("start_time"));
                                    toTextView.setText(jsonObject.getString("end_time"));
                                    addressTextView.setText(jsonObject.getString("address"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                         }
                    else {
                    }
                }catch (IOException e) {
                    Log.v("RUN", "Exception caught : ", e);
                }
            }
        });
    }

}
