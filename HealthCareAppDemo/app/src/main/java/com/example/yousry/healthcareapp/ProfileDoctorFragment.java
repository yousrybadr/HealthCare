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
import android.widget.Toast;
import android.widget.ViewSwitcher;

import AppClasses.Doctor;
import BackgroundTaskFolder.BackgroundTask;

/**
 * Created by yousry on 3/28/2016.
 */
public class ProfileDoctorFragment extends Fragment {

    EditText firstName_EditText;
    EditText lastName_EditText;
    EditText userName_EditText;
    EditText password_EditText;
    EditText rePassword_EditText;
    EditText email_EditText;
    EditText sex_EditText;
    EditText phone_EditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    boolean checking=false;
    Doctor doctor;

    public static ProfileDoctorFragment newInstance(Doctor doctor) {
        ProfileDoctorFragment fragment = new ProfileDoctorFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Doctor_obj", doctor);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile_doctor, container, false);
        //Intent i = getActivity().getIntent();
        //doctor =(Doctor)i.getSerializableExtra("Doctor_obj");
        doctor=new Doctor();
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("User_Preference", Context.MODE_PRIVATE);
        doctor.setFirstName(sharedpreferences.getString("first_name", "empty"));
        doctor.setLastName(sharedpreferences.getString("last_name", "empty"));
        doctor.setUserName(sharedpreferences.getString("user_name", "empty"));
        doctor.setPassword(sharedpreferences.getString("password", "empty"));
        doctor.setEmailAddress(sharedpreferences.getString("email", "empty"));
        doctor.setSex(sharedpreferences.getInt("sex", 0));
        doctor.setField(sharedpreferences.getString("field", "empty"));
        doctor.setPhone(sharedpreferences.getString("phone", "empty"));
        doctor.setImage(sharedpreferences.getString("image", "empty"));
        final TextView nameTextView=(TextView)view.findViewById(R.id.profile_doctor_textView_name);
        TextView userNameTextView=(TextView)view.findViewById(R.id.profile_doctor_textView_userName);
        TextView fieldTextView=(TextView)view.findViewById(R.id.profile_doctor_textView_field);
        TextView sexTextView=(TextView)view.findViewById(R.id.profile_doctor_textView_sex);
        TextView emailTextView=(TextView)view.findViewById(R.id.profile_doctor_textView_email);
        TextView phoneTextView=(TextView)view.findViewById(R.id.profile_doctor_textView_mobile);
        Button showClinics_btn=(Button)view.findViewById(R.id.profile_doctor_buuton_showClinics);
        Button createClinics_btn=(Button)view.findViewById(R.id.profile_doctor_buuton_createClinics);
        //final Button update_btn=(Button)view.findViewById(R.id.profile_doctor_buuton_update);



        final ViewSwitcher viewSwitcher=(ViewSwitcher)view.findViewById(R.id.profile_doctor_switcher);
        firstName_EditText= (EditText) viewSwitcher.findViewById(R.id.registeration_doctor_editText_firstName);
        lastName_EditText= (EditText) viewSwitcher.findViewById(R.id.registeration_doctor_editText_lastName);
        userName_EditText= (EditText) viewSwitcher.findViewById(R.id.registeration_doctor_editText_userName);
        password_EditText= (EditText) viewSwitcher.findViewById(R.id.registeration_doctor_editText_password);
        rePassword_EditText= (EditText) viewSwitcher.findViewById(R.id.registeration_doctor_editText_confirmPassword);
        email_EditText= (EditText) viewSwitcher.findViewById(R.id.registeration_doctor_editText_email);
        sex_EditText= (EditText) viewSwitcher.findViewById(R.id.registeration_doctor_editText_sex);
        phone_EditText= (EditText) viewSwitcher.findViewById(R.id.registeration_doctor_editText_phone);



        nameTextView.setText(doctor.getFirstName()+" "+doctor.getLastName());
        userNameTextView.setText(doctor.getUserName());
        fieldTextView.setText(doctor.getField());
        sexTextView.setText(doctor.getSex()==0 ? "male" : "female" );
        emailTextView.setText(doctor.getEmailAddress());
        phoneTextView.setText(doctor.getPhone());



        showClinics_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(),ShowClinicsActivity.class));
            }
        });

        createClinics_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(),CreateClinicActivity.class));

            }
        });
        viewSwitcher.setDisplayedChild(0);
        /*update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checking==false) //for Updating
                {

                    viewSwitcher.setDisplayedChild(1);
                    update_btn.setText("Done");
                    checking=true;

                    //Connection
                   // BackgroundTask backgroundTask=new BackgroundTask(getActivity().getApplicationContext());

                }
                else //for displaying
                {
                    viewSwitcher.setDisplayedChild(0);
                    update_btn.setText("Update");
                    checking=false;
                }

            }
        });
*/


  //      Toast.makeText(getActivity().getApplicationContext(),doctor.getField(),Toast.LENGTH_LONG).show();
        return view;
    }



}
