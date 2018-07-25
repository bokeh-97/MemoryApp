package com.example.shanthiroy.notification01;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final int NOTIFICATION_REMINDER_NIGHT=2;
    public static int currentRemId;
    public static String question;
    public static String answer;
    public static int freq[]={1,10,60};
    DBHelper mDBHelper;
    String TAG = "MainActivity";
     EditText questionBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBHelper=new DBHelper(this);
        Button dashboard_button = (Button) findViewById(R.id.dashboard_button);
        dashboard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Dashboard.class);
                startActivity(i);
            }
        });
        questionBox = (EditText)findViewById(R.id.questionBox);
        final EditText answerBox = (EditText)findViewById(R.id.answerBox);
        Button reminderButton = (Button)findViewById(R.id.reminderButton);
        questionBox.setOnKeyListener(onSoftKeyboardDonePress);
//...

// method not working:

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");



        // Set all alarms

        if (mDBHelper.getRowCount()>=1) {
//            int lastid = mDBHelper.getLastId();
//            for (int i = 1; i <= lastid; i++) {
//                String alarmtime = mDBHelper.remTimeFromId(i);
//
//                // convert alarmtime (String) to calendar object
//                Date date = new Date();
//
//                try {
//                    date = format.parse(alarmtime);
//                    Log.d(TAG, "String to date :" + (date));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                Log.d(TAG, "Reminder time :" + (date));
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(date);
//                AlarmSetter alarmsetter = new AlarmSetter();
//                alarmsetter.setContext(getApplicationContext());
//                alarmsetter.setAlarm(cal, i);
//
//            }
            AlarmChecker alarmchecker = new AlarmChecker(this);

            alarmchecker.SetAllAlarms();
        }

        //
            reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Reminder Set", Toast.LENGTH_SHORT).show();

                question = questionBox.getText().toString();
                answer = answerBox.getText().toString();
                mDBHelper.insertRow(question,answer);

                String alarmtime = mDBHelper.remTimeFromId(mDBHelper.getRowCount());

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
                AlarmSetter alarmsetter = new AlarmSetter();
                alarmsetter.setContext(getApplicationContext());
                alarmsetter.setAlarm(cal,mDBHelper.getRowCount());


                // String to date
//                Date date = new Date();
//                String remtime = mDBHelper.remTimeFromId(currentRemId);
//                try {
//                    date = format.parse(remtime);
//                    Log.d(TAG,"String to date :"+(date));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                Log.d(TAG,"Reminder time :"+(date));
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(date);
//                Log.d(TAG,"Rem min : "+cal.get(Calendar.MINUTE));





//                Intent notifyIntent = new Intent(MainActivity.this,MyReceiver.class);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast
//                        (getApplicationContext(), 3, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//                alarmManager.set(AlarmManager.RTC_WAKEUP,
//                        cal.getTimeInMillis(), pendingIntent);
            }
            });

    }
   private android.view.View.OnKeyListener onSoftKeyboardDonePress=new View.OnKeyListener()
    {
        public boolean onKey(View v, int keyCode, KeyEvent event)
        {
            if (event.getKeyCode() == KeyEvent.FLAG_EDITOR_ACTION)
            {
                // code to hide the soft keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(questionBox.getApplicationWindowToken(), 0);
            }
            return false;


        }
    };

}
