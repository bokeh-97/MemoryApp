package com.example.shanthiroy.notification01;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmChecker {
    public Context context;
    public String TAG =  "AlarmChecker";
    final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    DBHelper mDBHelper;


    public AlarmChecker(Context context) {
        this.context = context;
    }






    // Check if alarm is set
    public boolean AlarmSet(int id) {
        boolean alarmUp = (PendingIntent.getBroadcast(context, id + 1000,
                new Intent(context, MyReceiver.class),
                PendingIntent.FLAG_NO_CREATE) != null);
        return alarmUp;
    }


    // check all rows

    public void SetAllAlarms(){
        mDBHelper = new DBHelper(context);
        for(int i=1;i<=mDBHelper.getRowCount();i++){
            if(!AlarmSet(i)){

                Log.d(TAG,"alarm not set at id =" + i);
                String alarmtime = mDBHelper.remTimeFromId(i);

                // convert alarmtime (String) to calendar object
                Date date = new Date();

                try {
                    date = format.parse(alarmtime);
                    Log.d(TAG,"String to date :"+(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d(TAG,"Reminder time :"+(date));
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                Log.d(TAG,"alarm set for : id =  "+ i + "time :" + cal);
                AlarmSetter alarmsetter = new AlarmSetter();
                alarmsetter.setContext(context);

                alarmsetter.setAlarm(cal,i);




            }
            else
                Log.d(TAG,"alarm already set : id =="+i);
    }
  }


}

