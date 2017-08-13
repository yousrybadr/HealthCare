package com.example.yousry.healthcareapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by yousry on 3/1/2016.
 */
public class MyDialog extends DialogFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Add the buttons
        builder.setPositiveButton("Doctor", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(getActivity().getApplicationContext(),RegisterationDoctor.class));
            }
        });
        builder.setNegativeButton("Patient", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(getActivity().getApplicationContext(),RegistrationsPatient.class));

                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
