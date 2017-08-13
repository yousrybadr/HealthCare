package com.example.yousry.healthcareapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


/**
 * Created by mahmoud on 2016-04-19.
 */
public class MyService extends Service {
    private static final String URL_NOTIFICATION = "http://healthcareapp.16mb.com/healthcare/notification.php";
    private static String TAG = MyService.class.getSimpleName();
    private MyThread mythread;
    public boolean isRunning = false;
    final int notifyID = 1;
    Intent intent;
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        mythread  = new MyThread();
    }

    @Override
    public synchronized void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if(!isRunning){
            mythread.interrupt();
            mythread.stop();
        }
    }

    @Override
    public synchronized void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart");
        if(!isRunning){
            this.intent= new Intent(intent);
            mythread.start();
            isRunning = true;
        }
    }

    public void readNotification() throws IOException {
        SharedPreferences sharedpreferences = getSharedPreferences("User_Preference", Context.MODE_PRIVATE);

        int mYear,mMonth,mDay;
        Calendar c=Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        if(sharedpreferences.getString("type","empty").equals("PATIENT"))
        {
            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setReadTimeout(30, TimeUnit.SECONDS);
            client.setWriteTimeout(30, TimeUnit.SECONDS);
            RequestBody body =new FormEncodingBuilder()
                    .add("patient_id",String.valueOf(sharedpreferences.getInt("patient_id",0)))
                    .add("day", String.valueOf(mDay))
                    .add("month", String.valueOf(mMonth))
                    .add("year", String.valueOf(mYear))
                    .add("state","0")
                    .build();

            Request request = new Request.Builder().url(URL_NOTIFICATION).post(body).build();
            Log.d(TAG,"Task Executes");
            Response response = client.newCall(request).execute();
            Log.d(TAG,"Task finished");

            String jsonData =response.body().string();

            try {

                if(!jsonData.equalsIgnoreCase("")){
                    Log.d(TAG, "Got Response "+jsonData);
                    JSONArray jsonArray=new JSONArray(jsonData);
                    //for(int i=0;i<jsonArray.length();i++) {}


                    JSONObject jsonObject=jsonArray.getJSONObject(0);

                    NotificationManager mNotificationManager =
                            (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    //Notification manager getSystemService(Notification) to notify the system
                    // Sets an ID for the notification, so it can be updated
                   /* if(!sharedpreferences.contains("initialized")){
                         intent = new Intent(getApplicationContext(), LoginActivity.class);

                    }else {
                       }*/

                    intent.setClass(getApplicationContext(), ViewAppointmentActivity.class);//intent which will start when click on Notification
                    intent.putExtra("appId",String.valueOf(jsonObject.getInt("appointment_id")));
                    Log.d(TAG, "pass appointment value to ViewApointmentActivity ... value equals " + String.valueOf(jsonObject.getInt("appointment_id")));
                    int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
                    PendingIntent p = PendingIntent.getActivity(this, uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    //Log.d(TAG,"Appointment_id in Service = "+jsonObject.getInt("appointment_id"));
                    //PendingIntent p=PendingIntent.getActivity(getApplicationContext(),notifyID,intent,0);//to start intent from outside application
                    NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(getApplicationContext())//to build Notification
                            .setContentTitle("Health Care Notification")//to set title of Notification
                            .setContentText(jsonObject.getString("message"))//set massage of Notification
                            .setSmallIcon(R.mipmap.ic_icon_b)//set icon of Notification
                            .setAutoCancel(true)// make Notification Auto cancel when user click on notification
                            .setContentIntent(p)//set which intent will start when user click on Notification
                            .setGroup("Appointment")
                            .setGroupSummary(true)
                            .setNumber(jsonArray.length());

                    mNotificationManager.notify(notifyID, mNotifyBuilder.build());//notify user

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{

        }
    }

    class MyThread extends Thread{
        static final long DELAY = 60*1000;
        @Override
        public void run(){

            while(isRunning){
                Log.d(TAG, "Running");
                try {
                    readNotification();
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    isRunning = false;
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}