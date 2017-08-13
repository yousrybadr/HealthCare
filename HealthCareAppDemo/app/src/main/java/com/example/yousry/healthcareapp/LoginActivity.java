package com.example.yousry.healthcareapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import AppClasses.Doctor;
import AppClasses.Patient;
import BackgroundTaskFolder.BackgroundTask;
import InterfacesFiles.LoginValidation;

public class LoginActivity extends AppCompatActivity implements LoginValidation {

    private static final String LOGIN_DOCTOR_URL = "http://healthcareapp.16mb.com/healthcare/Login_doctor.php";
    private static final String LOGIN_PATIENT_URL = "http://healthcareapp.16mb.com/healthcare/Login_patient.php";

    public static String myUserType;
    Button mSignIn;
    TextView mSignUp;
    AutoCompleteTextView mUserName;
    EditText mPassword;
    String UserName="";
    String Password="";

    Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        mSignIn = (Button) findViewById(R.id.email_sign_in_button);
        mSignUp = (TextView) findViewById(R.id.signUpTextView);
        mUserName = (AutoCompleteTextView) findViewById(R.id.userName);
        mPassword = (EditText) findViewById(R.id.password);
        if( getIntent().getBooleanExtra("Exit me", false)){
            mUserName.setText(getIntent().getStringExtra("user_name"));
             //return;// add this to prevent from doing unnecessary stuffs
        }
        final RadioButton radioButton1= (RadioButton) findViewById(R.id.radioButton1);

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isEmpty() == false) {
                    BackgroundTask backgroundTask = new BackgroundTask(LoginActivity.this);
                    if (radioButton1.isChecked()) {
                        myUserType = "DOCTOR";
                        backgroundTask.setTag(BackgroundTask.TAGS.LOGIN_DOCTOR);
                        backgroundTask.setURL_KEY(LOGIN_DOCTOR_URL);
                        // RunOnBackGround(LOGIN_DOCTOR_URL, mUserName.getText().toString(),0);
                    } else {
                        myUserType = "PATIENT";
                        // RunOnBackGround(LOGIN_PATIENT_URL, mUserName.getText().toString(),1);
                        backgroundTask.setTag(BackgroundTask.TAGS.LOGIN_PATIENT);
                        backgroundTask.setURL_KEY(LOGIN_PATIENT_URL);
                    }
                    backgroundTask.setParamsItem(mUserName.getText().toString());
                    backgroundTask.execute(backgroundTask.getURL_KEY());
                    backgroundTask.setListener(new BackgroundTask.MyAsyncListener() {
                        @Override
                        public void onSuccessfulExecute(String data) {
                            try {
                                JSONArray jsonArray = new JSONArray(data);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                Password = jsonObject.getString("password");


                                if (myUserType.equals("DOCTOR")) {
                                    doctor = new Doctor();
                                    doctor.setID(jsonObject.getInt("doctor_id"));
                                    doctor.setFirstName(jsonObject.getString("first_name"));
                                    doctor.setLastName(jsonObject.getString("last_name"));
                                    doctor.setUserName(jsonObject.getString("user_name"));
                                    doctor.setPassword(jsonObject.getString("password"));
                                    doctor.setEmailAddress(jsonObject.getString("email"));
                                    doctor.setSex(jsonObject.getInt("sex"));
                                    doctor.setField(jsonObject.getString("field"));
                                    doctor.setPhone(jsonObject.getString("phone"));
                                    doctor.setImage(jsonObject.getString("image"));

                                    SharedPreferences sharedpreferences = getSharedPreferences("User_Preference", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putBoolean("initialized", true);

                                    editor.putInt("doctor_id", doctor.getID());
                                    editor.putString("type", myUserType);
                                    editor.putString("first_name", doctor.getFirstName());
                                    editor.putString("last_name", doctor.getLastName());
                                    editor.putString("user_name", doctor.getUserName());
                                    editor.putString("password", doctor.getPassword());
                                    editor.putString("email", doctor.getEmailAddress());
                                    editor.putInt("sex", doctor.getSex());
                                    editor.putString("field", doctor.getField());
                                    editor.putString("phone", doctor.getPhone());
                                    editor.putString("image", doctor.getImage());
                                    editor.commit();
                                } else if (myUserType.equals("PATIENT")) {
                                    Patient patient = new Patient();
                                    patient.setID(jsonObject.getInt("patient_id"));
                                    patient.setFirstName(jsonObject.getString("first_name"));
                                    patient.setLastName(jsonObject.getString("last_name"));
                                    patient.setUserName(jsonObject.getString("user_name"));
                                    patient.setPassword(jsonObject.getString("password"));
                                    patient.setEmailAddress(jsonObject.getString("email"));
                                    patient.setSex(jsonObject.getInt("sex"));
                                    patient.setPhone(jsonObject.getString("phone"));
                                    patient.setJob(jsonObject.getString("job"));
                                    patient.setImage(jsonObject.getString("image"));

                                    SharedPreferences sharedpreferences = getSharedPreferences("User_Preference", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putBoolean("initialized", true);

                                    editor.putInt("patient_id", patient.getID());
                                    editor.putString("type", myUserType);
                                    editor.putString("first_name", patient.getFirstName());
                                    editor.putString("last_name", patient.getLastName());
                                    editor.putString("user_name", patient.getUserName());
                                    editor.putString("password", patient.getPassword());
                                    editor.putString("email", patient.getEmailAddress());
                                    editor.putInt("sex", patient.getSex());
                                    editor.putString("job", patient.getJob());
                                    editor.putString("phone", patient.getPhone());
                                    editor.putString("image", patient.getImage());
                                    editor.commit();
                                }
                                //Toast.makeText(getApplicationContext(),doctor.getImage(),Toast.LENGTH_LONG).show();


                                if (isPasswordValid() == false) {
                                    mPassword.setError(getString(R.string.error_incorrect_password));
                                    mPassword.getText().clear();
                                    return;
                                } else {
                                    Intent intent;
                                    if (myUserType.equals("PATIENT")) {
                                        intent = new Intent(getApplicationContext(), HomePatient.class);

                                        startActivity(intent);
                                    } else {
                                        intent = new Intent(getApplicationContext(), HomeDoctor.class);

                                        startActivity(intent);
                                    }

                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });


                }else
                {
                    mUserName.setError("This Field is Empty ,please Fill it");
                }
            }


        });


        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDialog myDialog = new MyDialog();
                myDialog.show(getFragmentManager(), "sd");
            }
        });
    }

    @Override
    public boolean isUserNameValid() {
        if (UserName.equals(mUserName.getText().toString())) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isPasswordValid() {
        if (Password.equals(mPassword.getText().toString())) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        if ((mUserName.getText().toString().isEmpty()) && (mPassword.getText().toString().isEmpty())) {
            Toast.makeText(getApplicationContext(), "Enter User Name and Password ", Toast.LENGTH_LONG).show();
            return true;
        } else if (mUserName.getText().toString().isEmpty()) {
            mUserName.setError("Enter User Name");
            return true;
        } else if (mPassword.getText().toString().isEmpty()) {
            mPassword.setError("Enter Password");
            return true;
        } else
            return false;
    }









    //Connection

   /* public void RunOnBackGround(String url,String userName, final Integer flag)  {

        final Handler handler=new Handler(Looper.getMainLooper());
        final String[] jsonData = new String[1];
        final OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormEncodingBuilder()
                .add("user_name", userName)
                .build();;


        Request request = new Request.Builder().url(url).post(body).build();
        final Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //No data found username is exist but there is error in connection
                //Ex. Timeout error or 404
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    jsonData[0] = response.body().string();


                    JSONArray jsonArray= new JSONArray(jsonData[0]);
                    final JSONObject jsonObject = jsonArray.getJSONObject(0);


                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Password =jsonObject.getString("password");
                                if (isEmpty() == false) {
                                    if (isPasswordValid() == false)
                                    {
                                        mPassword.setError(getString(R.string.error_incorrect_password));
                                        mPassword.getText().clear();
                                        return;
                                    }
                                    else
                                    {
                                        Intent intent=new Intent(getApplicationContext(),HomeDoctor.class);
                                        intent.putExtra("TYPE",myUserType);
                                        intent.putExtra("obj",jsonData[0]);
                                        startActivity(intent);
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    });
                    if(response.isSuccessful()) {

                    }
                    else {
                    }
                }catch (IOException e) {
                    Log.v("RUN", "Exception caught : ", e);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
*/

}
