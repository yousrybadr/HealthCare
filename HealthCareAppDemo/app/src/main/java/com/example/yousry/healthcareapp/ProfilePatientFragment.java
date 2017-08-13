package com.example.yousry.healthcareapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import AppClasses.Doctor;
import AppClasses.Patient;

/**
 * Created by yousry on 4/15/2016.
 */
public class ProfilePatientFragment extends Fragment {

    EditText firstName_EditText;
    EditText lastName_EditText;
    EditText userName_EditText;
    EditText password_EditText;
    EditText rePassword_EditText;
    EditText email_EditText;
    EditText sex_EditText;
    EditText phone_EditText;

    Button historyButton;
    Button updateButton;

    Patient patient;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile_patient, container, false);

        patient=new Patient();
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("User_Preference", Context.MODE_PRIVATE);
        patient.setFirstName(sharedpreferences.getString("first_name", "empty"));
        patient.setLastName(sharedpreferences.getString("last_name", "empty"));
        patient.setUserName(sharedpreferences.getString("user_name", "empty"));
        patient.setPassword(sharedpreferences.getString("password", "empty"));
        patient.setEmailAddress(sharedpreferences.getString("email", "empty"));
        patient.setSex(sharedpreferences.getInt("sex", 0));
        patient.setPhone(sharedpreferences.getString("phone", "empty"));
        patient.setJob(sharedpreferences.getString("job","empty"));
        patient.setImage(sharedpreferences.getString("image", "empty"));
        final TextView nameTextView=(TextView)view.findViewById(R.id.profile_patient_textView_name);
        TextView userNameTextView=(TextView)view.findViewById(R.id.profile_patient_textView_userName);
        TextView emailTextView=(TextView)view.findViewById(R.id.profile_patient_textView_email);
        TextView sexTextView=(TextView)view.findViewById(R.id.profile_patient_textView_sex);
        TextView phoneTextView=(TextView)view.findViewById(R.id.profile_patient_textView_phone);
        TextView jobTextView=(TextView)view.findViewById(R.id.profile_patient_textView_job);

        historyButton = (Button) view.findViewById(R.id.profile_patient_button_history);

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(),HistoryActivity.class));
            }
        });

        nameTextView.setText(patient.getFirstName()+" "+patient.getLastName());
        userNameTextView.setText(patient.getUserName());
        sexTextView.setText(patient.getSex()==0 ? "male" : "female" );
        emailTextView.setText(patient.getEmailAddress());
        phoneTextView.setText(patient.getPhone());
        jobTextView.setText(patient.getJob());



        return view;
    }
}
