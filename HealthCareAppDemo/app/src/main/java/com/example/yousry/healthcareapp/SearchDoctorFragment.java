package com.example.yousry.healthcareapp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

/**
 * Created by yousry on 3/28/2016.
 */
public class SearchDoctorFragment extends Fragment {
    private static final String LOGIN_PATIENT_URL = "http://healthcareapp.16mb.com/healthcare/Login_patient.php";

    AutoCompleteTextView autoCompleteTextView;
    String mToast;
    String [] Doctor_Names;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_doctor, container, false);

        //autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.count);
        //Doctor_Names =  getResources().getStringArray(R.array.doctor_names);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, Doctor_Names);
        //autoCompleteTextView.setAdapter(adapter);
        //passData("meke");
        return view;
    }


    OnDataPass dataPasser;

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }
    public void passData(String data) {
        dataPasser.onDataPass(data);
    }
    public interface OnDataPass {
        public void onDataPass(String data);
    }

}
