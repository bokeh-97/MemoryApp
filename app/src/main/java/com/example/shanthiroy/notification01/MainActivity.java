package com.example.shanthiroy.notification01;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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

        questionBox.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        questionBox.setHorizontallyScrolling(false);

        final EditText answerBox = (EditText)findViewById(R.id.answerBox);
        Button reminderButton = (Button)findViewById(R.id.reminderButton);



        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");



        // Set all alarms

        if (mDBHelper.getRowCount()>=1) {


            AlarmChecker alarmchecker = new AlarmChecker(this);

            alarmchecker.SetAllAlarms();
        }


            reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (questionBox.getText().toString().equals("") || answerBox.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "EMPTY FIELDS! ", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            questionBox.setText("");
                            answerBox.setText("");
                        }
                    }, 2000);


                } else {


                    question = questionBox.getText().toString();
                    answer = answerBox.getText().toString();


                    Toast.makeText(getApplicationContext(), "Reminder Set", Toast.LENGTH_SHORT).show();
                    mDBHelper.insertRow(question, answer);

                    String alarmtime = mDBHelper.remTimeFromId(mDBHelper.getRowCount());

                    // convert alarmtime (String) to calendar object
                    Date date = new Date();

                    try {
                        date = format.parse(alarmtime);
                        Log.d(TAG, "String to date :" + (date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "Reminder time :" + (date));
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    AlarmSetter alarmsetter = new AlarmSetter();
                    alarmsetter.setContext(getApplicationContext());
                    alarmsetter.setAlarm(cal, mDBHelper.getRowCount());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            questionBox.setText("");
                            answerBox.setText("");
                        }
                    }, 2001);


                }
            }
            });




    }


}
