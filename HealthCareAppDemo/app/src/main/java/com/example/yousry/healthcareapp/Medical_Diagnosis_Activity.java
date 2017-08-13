package com.example.yousry.healthcareapp;

import android.annotation.TargetApi;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import AppClasses.Expert_system;

public class Medical_Diagnosis_Activity extends AppCompatActivity {

    Expert_system expert_system  = new Expert_system();
    public Spinner spinner_symptomps ;
    public ArrayAdapter<String> Symptomps_adapter ;
    public ListView listview_Input;
    public ArrayAdapter<String> Input_adapter;
    public String[] Input_Symptomps ;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_diagnosis_layout);


       read_from_features_File_to_Features_array(expert_system);


        spinner_symptomps = (Spinner) findViewById(R.id.spinner_symptomps_id);

        //Symptomps_adapter =  new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, expert_system.getFeatures());
        Symptomps_adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item ,expert_system.getFeatures() );
        Symptomps_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner_symptomps.setAdapter(Symptomps_adapter);

        Input_adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1 );

        //spinner_symptomps.setForegroundTintList(ColorStateList.valueOf(Color.RED));
        
        spinner_symptomps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " Aded", Toast.LENGTH_SHORT).show();
                Input_adapter.add(parent.getItemAtPosition(position).toString());
                listview_Input = (ListView) findViewById(R.id.listveiw_sympots);
                listview_Input.setAdapter(Input_adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        


    }

    private void read_from_features_File_to_Features_array(Expert_system expert_system) {
        String word = "X";
        int Diseases_Num =0;
        AssetManager assetManager;
        try {
            assetManager = getAssets();
            InputStream inputf;
            inputf = assetManager.open("test.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputf));

            word =  reader.readLine();
            Diseases_Num =Integer.valueOf(word);

            word =  reader.readLine();
            expert_system.setFeatures(word.split("-"));

            expert_system.Clases = new String[Diseases_Num];
            expert_system.Train_data = new int[Diseases_Num][expert_system.getFeatures().length];
            expert_system.Result = new double[Diseases_Num][2];
            int line =-1;

            while(word != null)
            {
                word = reader.readLine();
                if (word != null){
                    char fch = word.charAt(0);
                    if(fch =='#') {
                        line++;
                        expert_system.Clases[line] = word.substring(1);
                    }
                    else {
                        for(int i=0 ; i< expert_system.getFeatures().length ; i++)
                        {
                            if(word.equals(expert_system.Features[i])==true)
                            {
                                expert_system.Train_data[line][i] =1;
                                break;
                            }
                        }
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void make_decision(View view) {

        expert_system.fill_input_features(Input_adapter);

        expert_system.KNN_Algorithm_calculate_Eculidian_distance();

        boolean X =  expert_system.get_index_of_min_distance_else_error();

        if(X== true){

            Toast.makeText(getApplicationContext(),"that belong to Class "+expert_system.getDetected_issue(),Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "can't indenify the issue please try again", Toast.LENGTH_SHORT).show();
        }


    }
}
