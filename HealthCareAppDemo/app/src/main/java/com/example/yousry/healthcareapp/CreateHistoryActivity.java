package com.example.yousry.healthcareapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import BackgroundTaskFolder.BackgroundTask;

public class CreateHistoryActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String URL_ADD_HISTORY ="http://healthcareapp.16mb.com/healthcare/add_history.php";
    EditText descriptionEditText;
    EditText drugEditText;
    EditText noteEditText;
    TextView doctor_nameTextView;
    TextView dateTextView;
    Button doneButton;
    BackgroundTask backgroundTask;

    String mDoctorName;
    int mYear,mMonth,mDay;
    int doctor_id;
    int patient_id ;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_history);
        descriptionEditText = (EditText) findViewById(R.id.createHistory_editText_caseDescription);
        drugEditText = (EditText) findViewById(R.id.createhistory_editText_drug);
        noteEditText = (EditText) findViewById(R.id.createHistory_editText_note);
        doctor_nameTextView = (TextView) findViewById(R.id.createHistory_textView_doctorName);
        dateTextView = (TextView) findViewById(R.id.createHistory_textView_date);
        doneButton = (Button) findViewById(R.id.createHistory_button);

        calendar =Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        String date = " "+mDay+"-"+mMonth+"-"+mYear+" ";

        patient_id = getIntent().getIntExtra("patient_id", 0);

        SharedPreferences sharedPreferences=getSharedPreferences("User_Preference", Context.MODE_PRIVATE);
        doctor_id = sharedPreferences.getInt("doctor_id",0);
        String fname = sharedPreferences.getString("first_name","EMPTY");
        String lname = sharedPreferences.getString("last_name","EMPTY");

        doctor_nameTextView.setText(fname +" "+lname);
        dateTextView.setText(date);

        doneButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.createHistory_button:
                backgroundTask = new BackgroundTask(CreateHistoryActivity.this);
                backgroundTask.setTag(BackgroundTask.TAGS.ADD_HISTORY);
                backgroundTask.setParamsItem(String.valueOf(patient_id)); //patient id
                backgroundTask.setParamsItem(String.valueOf(doctor_id)); //doctor_id
                backgroundTask.setParamsItem(doctor_nameTextView.getText().toString());//doctor_name
                backgroundTask.setParamsItem(descriptionEditText.getText().toString());//case description
                backgroundTask.setParamsItem(drugEditText.getText().toString());//drug
                backgroundTask.setParamsItem(noteEditText.getText().toString());//note
                backgroundTask.setParamsItem(String.valueOf(mDay));//day
                backgroundTask.setParamsItem(String.valueOf(mMonth));//month
                backgroundTask.setParamsItem(String.valueOf(mYear));//year
                /*Toast.makeText(getApplicationContext()
                        ,String.valueOf(getIntent().getIntExtra("patient_id", 0))
                        + " "+ mDay +" "
                        , Toast.LENGTH_LONG).show();*/
                backgroundTask.execute(URL_ADD_HISTORY);
                backgroundTask.setListener(new BackgroundTask.MyAsyncListener() {
                    @Override
                    public void onSuccessfulExecute(String data) {
                        if(data.equals("No Request Body"))
                        {
                            Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();

                        }
                        else if(data ==null)
                        {
                            Toast.makeText(getApplicationContext(),"Connection Error",Toast.LENGTH_LONG).show();

                        }
                        else{
                            Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int data = patient_id;
        Intent intent = new Intent();
        intent.putExtra("patient_id", data);
        setResult(RESULT_OK, intent);
    }
}
