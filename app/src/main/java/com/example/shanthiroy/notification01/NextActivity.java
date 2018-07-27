package com.example.shanthiroy.notification01;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.shanthiroy.notification01.MainActivity.currentRemId;
import static com.example.shanthiroy.notification01.MainActivity.question;
import static java.util.Calendar.*;

public class NextActivity extends AppCompatActivity {
    DBHelper myDBHelper;
    String TAG = "NextActivity";
    TextView questionReminder;
    EditText answerReminder;
    int currentID;
    TextView answerDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        currentID = i.getIntExtra("currentID", 0);
        Log.d(TAG, "current id : " + currentID);
        myDBHelper = new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        if (!myDBHelper.idCheckExisting(currentID)) {
            Toast.makeText(getApplicationContext(), "The reminder was deleted", Toast.LENGTH_SHORT).show();
           Intent j = new Intent(NextActivity.this, MainActivity.class);
           startActivity(j);


        }
        else{

        questionReminder = (TextView) findViewById(R.id.questionReminder);
        questionReminder.setText(myDBHelper.getQuestion(currentID));
        answerReminder = (EditText) findViewById(R.id.answerReminder);
        Button answerCheck = (Button) findViewById(R.id.answerCheck);
        answerDisplay = (TextView) findViewById(R.id.answerDisplay);
        answerCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);


                Log.d(TAG, "currentID : " + currentID);
                String correctAnswer = myDBHelper.getAnswer(currentID);
                Log.d(TAG, "correct answer" + correctAnswer);
                Log.d(TAG, "answerReminder.getText().toString() " + answerReminder.getText().toString());
                if (correctAnswer.replaceAll("\\s+","").equalsIgnoreCase(answerReminder.getText().toString().replaceAll("\\s+",""))) {


                    //Toast.makeText(getApplicationContext(), "Correct Answer. Next reminder in 3 mins", Toast.LENGTH_SHORT).show();
                    // cancel existing alarm

                    // display result in answerDisplay

                    answerDisplay.setText("Correct Answer. Next reminder in 3 mins");
                    answerDisplay.setVisibility(View.VISIBLE);

                    Intent notifyIntent_N = new Intent(NextActivity.this, MyReceiver.class);

                    PendingIntent pendingIntent_N = PendingIntent.getService
                            (getApplicationContext(), currentID + 1000, notifyIntent_N, PendingIntent.FLAG_UPDATE_CURRENT);

                    Log.d(TAG, "PENDING INTENT CREATER UID = " + pendingIntent_N.getCreatorUid());

                    AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

                    try {
                        alarmManager.cancel(pendingIntent_N);
                    } catch (Exception e) {
                        Log.e(TAG, "AlarmManager update was not canceled. " + e.toString());
                    }
                    // check if cancelleds
                    boolean alarmUp = (PendingIntent.getBroadcast(getApplicationContext(), currentID + 1000,
                            new Intent(getApplicationContext(), MyReceiver.class),
                            PendingIntent.FLAG_NO_CREATE) != null);
                    Log.d(TAG, "alarm exists ? :" + alarmUp);

                    //update rem time to +30 mins from current system time
                    myDBHelper.updateRemtime(currentID, 3);


                    AlarmChecker alarmchecker = new AlarmChecker(getApplicationContext());
                    alarmchecker.SetAllAlarms();
                    finish();

                } else {
                    //update rem time to +10mins from current system time
                    Log.d(TAG, "mcurrentid in else" + currentID);

                    // // display result in answerDisplay

                    answerDisplay.setText("Wrong Answer !! \n " + "Correct answer : " + correctAnswer + "\n Next Reminder in 1 min");
                    answerDisplay.setVisibility(View.VISIBLE);
                    //  Toast.makeText(getApplicationContext(), "Wrong Answer. [ Correct answer :"+correctAnswer+" Next Rem in 1 min", Toast.LENGTH_SHORT).show();

                    // cancel existing alarm

                    Intent notifyIntent_N = new Intent(NextActivity.this, MyReceiver.class);

                    PendingIntent pendingIntent_N = PendingIntent.getService
                            (getApplicationContext(), currentID + 1000, notifyIntent_N, PendingIntent.FLAG_UPDATE_CURRENT);
                    Log.d(TAG, "PENDING INTENT CREATER UID = " + pendingIntent_N.getCreatorUid());


                    AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    try {
                        alarmManager.cancel(pendingIntent_N);
                    } catch (Exception e) {
                        Log.e(TAG, "AlarmManager update was not canceled. " + e.toString());
                    }

                    // check if cancelled
                    boolean alarmUp = (PendingIntent.getBroadcast(getApplicationContext(), currentID + 1000,
                            new Intent(getApplicationContext(), MyReceiver.class),
                            PendingIntent.FLAG_NO_CREATE) != null);
                    Log.d(TAG, "alarm exists ? :" + alarmUp);

                    //update rem time to +10 mins from current system time
                    myDBHelper.updateRemtime(currentID, 1);

                    AlarmChecker alarmchecker = new AlarmChecker(getApplicationContext());
                    alarmchecker.SetAllAlarms();
                    finish();

                }

            }

        });


    }

        /* date to string to date

        // DATE TO STRING

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

            Calendar cal = Calendar.getInstance();
            String datetime = dateformat.format(cal.getTime());
            Log.d(TAG, "Date time = " + datetime);

            //String to date

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date= new Date();
        try {
            date = format.parse(datetime);
            Log.d(TAG,"String to date :"+(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal2 = Calendar.getInstance();
        cal.setTime(date);
        int hours = cal2.get(Calendar.HOUR_OF_DAY);
        int days = cal2.get(Calendar.DAY_OF_MONTH);
        int mins =  cal2.get(Calendar.MINUTE);
        Log.d(TAG,"hour:"+(hours));
        Log.d(TAG,"days::"+(days));
        Log.d(TAG,"mins::"+(mins));

        */






    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(NextActivity.this, MainActivity.class));
        finish();

    }
}
