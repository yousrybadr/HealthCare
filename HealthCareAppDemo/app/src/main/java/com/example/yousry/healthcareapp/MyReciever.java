package com.example.yousry.healthcareapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.widget.Toast;

/**
 * Created by mahmoud on 2016-04-19.
 */
public class MyReciever extends BroadcastReceiver {
    Context myContext;
    Intent myIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        //Toast.makeText(context, "MyReceiver Started", Toast.LENGTH_SHORT).show();
        this.myContext =context;
        this.myIntent=new Intent(context,MyService.class);

        if (isOnline(myContext)) {
            myContext.startService(myIntent);
        } else{
            myContext.stopService(myIntent);
        }



    }

    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in air plan mode it will be null
        return (netInfo != null && netInfo.isConnected());

    }
}
