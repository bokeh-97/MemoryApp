package com.example.shanthiroy.notification01;

import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import static com.example.shanthiroy.notification01.MainActivity.question;

public class MyNewIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 3;

    public MyNewIntentService() {
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

      //  String KEY_REPLY = "key_reply";
       // Notification.Builder builder = new Notification.Builder(this);
        //builder.setContentTitle("My Title");
        //builder.setContentText("This is the Body");
        //builder.setSmallIcon(R.drawable.ic_add_alert_black_24dp);


//        Intent notifyIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        //to be able to launch your activity from the notification
//        builder.setContentIntent(pendingIntent);
//        Notification notificationCompat = builder.build();
//        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
//        managerCompat.notify(NOTIFICATION_ID, notificationCompat);

        Intent intent2 = new Intent(getApplicationContext(),NextActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent2, 0);

//        String replyLabel = "Enter your reply here";
//
//        RemoteInput remoteInput = new RemoteInput.Builder(KEY_REPLY)
//                .setLabel(replyLabel)
//
//
//                .build();
//
//
//        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
//                android.R.drawable.sym_action_chat, "REPLY", pendingIntent)
//                .addRemoteInput(remoteInput)
//                .setAllowGeneratedReplies(true)
//                .build();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setAutoCancel(false);
        builder.setTicker("this is ticker text");
        builder.setContentTitle(question);
        builder.setContentText("Click to answer");
        builder.setSmallIcon(R.drawable.ic_add_alert_black_24dp);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setSubText(" ");   //API level 16
        builder.setNumber(100);
        builder.build();
        //builder.addAction(replyAction);

       Notification notificationCompat =builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);





    }
}
