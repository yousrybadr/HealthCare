package com.example.yousry.healthcareapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by mahmoud on 2016-04-17.
 */
public class CustomDialogClass extends Dialog implements View.OnClickListener {
    public Context context;
    OnMyDialogResult mDialogResult ;

    public Dialog d;
    public Button bookButton, cancelButton, dateButton,timeButton;
    private int mYear , mMonth, mDay, mHour, mMinute;

    EditText dateEditText , timeEditText;
    public CustomDialogClass(Context a) {
        super(a);
        this.context=a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_layout);
        bookButton = (Button) findViewById(R.id.custom_dialog_button_book);
        cancelButton = (Button) findViewById(R.id.custom_dialog_button_cancel);
        dateButton = (Button) findViewById(R.id.custom_dialog_button_setDate);
        timeButton = (Button) findViewById(R.id.custom_dialog_button_setTime);

        dateEditText = (EditText) findViewById(R.id.custom_dialog_editText_date);
        timeEditText = (EditText) findViewById(R.id.custom_dialog_editText_time);


        bookButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        dateButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final Calendar c;

        switch (v.getId())
        {
            case R.id.custom_dialog_button_book:
                if(!isEmpty())
                {
                    // Connection
                    if( mDialogResult != null ){
                        mDialogResult.onResult(true,mYear,mMonth, mDay,mHour,mMinute);
                        dismiss();
                    }
                    else {
                        dateEditText.setError("Fill This Field");
                        timeEditText.setError("Fill This Field");
                    }

                }
                break;
            case R.id.custom_dialog_button_cancel:
                dismiss();
                break;
            case R.id.custom_dialog_button_setDate:
                c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateEditText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                mYear=year;
                                mMonth=monthOfYear;
                                mDay=dayOfMonth;

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.custom_dialog_button_setTime:
                // Get Current Time
                c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timeEditText.setText(hourOfDay + ":" + minute);
                                mHour=hourOfDay;
                                mMinute=minute;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;

        }
    }

    private boolean isEmpty()
    {
        if(dateEditText.getText().toString().isEmpty() ||timeEditText.getText().toString().isEmpty())
        {
            return true;
        }
        return false;

    }
    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }
    public interface OnMyDialogResult{
        void onResult(boolean result, int mYear, int mMonth, int mDay, int mHour, int mMinute);
    }
}
