package com.example.shanthiroy.notification01;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.shanthiroy.notification01.MainActivity.question;
import static java.util.Calendar.*;

public class NextActivity extends AppCompatActivity {
    DBHelper myDBHelper;
    String TAG = "NextActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        TextView questionReminder = (TextView) findViewById(R.id.questionReminder);
        EditText answerReminder = (EditText)findViewById(R.id.answerReminder);

        myDBHelper = new DBHelper(this);
        //Cursor c = myDBHelper.PullTable();
       // questionReminder.setText(c.getString(1));

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
}
