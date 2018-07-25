package com.example.shanthiroy.notification01;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class AlarmSetter {
    String TAG = "AlarmSetter";

    Context context;
    void setContext(Context context){
        this.context = context;

    }

    boolean setAlarm(Calendar cal, int id){

        Intent notifyIntent = new Intent(context,MyReceiver.class);
        notifyIntent.putExtra("currentID",id);
//        context.sendBroadcast(notifyIntent);
        Log.d(TAG,"currentID  : "+id);


        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (context, id+1000, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                cal.getTimeInMillis(), pendingIntent);


    //if alarm setting happened return true, else return false
        return true;
    }

}
