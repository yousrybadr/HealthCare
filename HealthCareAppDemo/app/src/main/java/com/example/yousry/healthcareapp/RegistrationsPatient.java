package com.example.yousry.healthcareapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import AppClasses.MainFunctions;
import AppClasses.Patient;
import AppClasses.ValidPassword;
import BackgroundTaskFolder.BackgroundTask;
import InterfacesFiles.RegisterationValidation;

/**
 * Created by yousry on 3/1/2016.
 */
public class RegistrationsPatient extends AppCompatActivity implements RegisterationValidation {
    private static final int REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST = 1888;
    private static final String URL_SIGNUP_PATIENT="http://healthcareapp.16mb.com/healthcare/Register_patient.php";
    public Patient patient_obj;


    ImageView imageView;
    Button takePhoto;
    Button choosePhoto;
    Button doneButton;
    Bitmap bitmap;

    String convertedImage;

    EditText firstName_EditText;
    EditText lastName_EditText;
    EditText userName_EditText;
    EditText password_EditText;
    EditText rePassword_EditText;
    EditText email_EditText;
    EditText sex_EditText;
    EditText phone_EditText;
    EditText job_EditText;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.registeration_patient_layout);

        imageView= (ImageView) findViewById(R.id.registeration_patient_imageView);
        takePhoto= (Button) findViewById(R.id.registeration_patient_button_take_photo);
        choosePhoto= (Button) findViewById(R.id.registeration_patient_button_choose_photo);
        doneButton= (Button) findViewById(R.id.registeration_patient_button_done);
        firstName_EditText= (EditText) findViewById(R.id.registeration_patient_editText_firstName);
        lastName_EditText= (EditText) findViewById(R.id.registeration_patient_editText_lastName);
        userName_EditText= (EditText) findViewById(R.id.registeration_patient_editText_userName);
        password_EditText= (EditText) findViewById(R.id.registeration_patient_editText_password);
        rePassword_EditText= (EditText) findViewById(R.id.registeration_patient_editText_confirmPassword);
        email_EditText= (EditText) findViewById(R.id.registeration_patient_editText_email);
        sex_EditText= (EditText) findViewById(R.id.registeration_patient_editText_sex);
        phone_EditText= (EditText) findViewById(R.id.registeration_patient_editText_phone);
        job_EditText = (EditText) findViewById(R.id.registeration_patient_editText_job);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });



        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = null;
                pickImage(v);
            }
        });



        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidPassword validPassword = isPasswordValid(password_EditText.getText().toString());
                if (isEmpty() == false) {
                    if (validPassword.state) {
                        if (isEmailAddressValid(email_EditText.getText().toString())) {
                            if (isPasswordConfirmationValid(password_EditText.getText().toString())) {
                                BackgroundTask backgroundTask = new BackgroundTask(RegistrationsPatient.this);

                                backgroundTask.setTag(BackgroundTask.TAGS.REGISTERATION_PATIENT);
                                backgroundTask.setParamsItem(firstName_EditText.getText().toString());
                                backgroundTask.setParamsItem(lastName_EditText.getText().toString());
                                backgroundTask.setParamsItem(userName_EditText.getText().toString());
                                backgroundTask.setParamsItem(password_EditText.getText().toString());
                                backgroundTask.setParamsItem(email_EditText.getText().toString());
                                backgroundTask.setParamsItem(phone_EditText.getText().toString());
                                if (sex_EditText.getText().toString().equals("male"))
                                    backgroundTask.setParamsItem("0");
                                else
                                    backgroundTask.setParamsItem("1");

                                backgroundTask.setParamsItem(job_EditText.getText().toString());
                                backgroundTask.setParamsItem("yoyo");
                                backgroundTask.execute(URL_SIGNUP_PATIENT);//URL Param[0]

                                backgroundTask.setListener(new BackgroundTask.MyAsyncListener() {
                                    @Override
                                    public void onSuccessfulExecute(String data) {
                                        if (data != null) {
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                       // Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else{rePassword_EditText.setError("this text is not same");}
                        }else{email_EditText.setError("Email Field is not valid");}
                    }else{password_EditText.setError(validPassword.message);}
                }
            }
        });
    }
    public void pickImage(View View) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
        onActivityResult(REQUEST_CODE, 1, intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {


                // We need to recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }
                InputStream stream = getContentResolver().openInputStream(
                        data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                convertedImage= MainFunctions.BitMapToString(bitmap);
                stream.close();
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }


    @Override
    public boolean isEmailAddressValid(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public ValidPassword isPasswordValid(String password) {
        ValidPassword valid=new ValidPassword();

        //return true if and only if password:
        //1. have at least eight characters.
        //2. consists of only letters and digits.
        //3. must contain at least two digits.
        if (password.length() < 8) {
            valid.message="your Password's Length must be greater than 8 letters";
            valid.state =false;
            return valid;
        } else {
            char c;
            int count = 1;
            for (int i = 0; i < password.length() - 1; i++) {
                c = password.charAt(i);
                if (!Character.isLetterOrDigit(c)) {
                    valid.message="your Password's String must contain one Letter at least";
                    valid.state =false;
                    return valid;
                } else if (Character.isDigit(c)) {
                    count++;
                    if (count < 2)   {
                        valid.message="your Password's Length must consist of one Digit";
                        valid.state =false;
                        return valid;
                    }
                }
            }
        }
        valid.message ="true";
        valid.state =true;
        return valid;
    }

    @Override
    public boolean isPasswordConfirmationValid(String password) {
        if(password.equals(rePassword_EditText.getText().toString()))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean isJobEmpty() {
        if(job_EditText.getText().toString().isEmpty())
        {
            job_EditText.setError("Job is Empty");
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        if(!firstName_EditText.getText().toString().isEmpty()) {
            if(!lastName_EditText.getText().toString().isEmpty()) {
                if(!userName_EditText.getText().toString().isEmpty()) {
                    if(!email_EditText.getText().toString().isEmpty()) {
                        if (!password_EditText.getText().toString().isEmpty()) {
                            if(!sex_EditText.getText().toString().isEmpty()){
                                if(!phone_EditText.getText().toString().isEmpty()){
                                    if(!isJobEmpty()) {
                                        return false;
                                    }
                                    else{
                                        return true;
                                    }
                                }
                                else{
                                    phone_EditText.setError("Phone Field is Empty");
                                    return true;
                                }
                            }
                            else{
                                sex_EditText.setError("Sex Field is Empty");
                                return true;
                            }
                        }
                        else{
                            password_EditText.setError("Password is Empty");
                            return true;
                        }
                    }
                    else {
                        email_EditText.setError("Email Filed is Empty");
                        return true;
                    }
                }
                else{
                    userName_EditText.setError("UserName Field is Empty");
                    return true;
                }
            }
            else {
                lastName_EditText.setError("Last Name is Empty");
                return true;
            }
        }
        else{
            firstName_EditText.setError("First Name is Empty");
            return true;
        }
    }
}
