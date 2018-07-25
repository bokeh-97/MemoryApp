package com.example.shanthiroy.notification01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    String TAG = "MyReceiver";


    public MyReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"onReceive");

        int currentID = intent.getIntExtra("currentID",0);
        Log.d(TAG,"current id : "+currentID);
        Intent intent1 = new Intent(context, MyNewIntentService.class);
        intent1.putExtra("currentID",currentID);
        context.sendBroadcast(intent1);
        context.startService(intent1);

    }
}
