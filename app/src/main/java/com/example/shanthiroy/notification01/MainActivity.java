package com.example.shanthiroy.notification01;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final int NOTIFICATION_REMINDER_NIGHT=2;
    public static String question;
    public static String answer;
    public static int freq[]={1,10,60};
    DBHelper mDBHelper;
    String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBHelper=new DBHelper(this);
        final EditText questionBox = (EditText)findViewById(R.id.questionBox);
        final EditText answerBox = (EditText)findViewById(R.id.answerBox);
        Button reminderButton = (Button)findViewById(R.id.reminderButton);










        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Reminder Set", Toast.LENGTH_SHORT).show();

                question = questionBox.getText().toString();
                answer = answerBox.getText().toString();
                mDBHelper.insertRow(question,answer);
                int currentId=mDBHelper.getCurrentID();
               // int count = mDBHelper.getCurrentCount(currentId);


                // String to date


                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date date = new Date();
                String remtime = mDBHelper.remTimeFromId(mDBHelper.findMinId());
                try {
                    date = format.parse(remtime);
                    Log.d(TAG,"String to date :"+(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d(TAG,"Reminder time :"+(date));
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                Log.d(TAG,"Rem min : "+cal.get(Calendar.MINUTE));



                Intent notifyIntent = new Intent(MainActivity.this,MyReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast
                        (getApplicationContext(), 3, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        cal.getTimeInMillis(), pendingIntent);
            }
            });

         }




}
