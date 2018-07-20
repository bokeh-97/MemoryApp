package com.example.shanthiroy.notification01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "QuestionBank.db";
    String TAG = "DBHelper";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table questionbank " +
                        "(id integer primary key, question text,answer text,remindertime text);"
        );
        Log.d(TAG,"table created");
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS questionbank");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertRow(String mquestion, String manswer){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        //Calculate first reminder time
        //Date to string
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
       Calendar cal = Calendar.getInstance();
       cal.add(Calendar.MINUTE,2);
        String first_rem = dateformat.format(cal.getTime());
        Log.d(TAG,"first rem:"+first_rem);




// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("question", mquestion);
        values.put("answer", manswer);
        values.put("remindertime",first_rem);

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("questionbank", null, values);
        return true;
    }
    public Cursor PullTable(){

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {"id","question","answer","remindertime"};
        Cursor cursor = db.query("questionbank",columns , null, null, null, null, null);
        cursor.moveToLast();
       return cursor;
  }


    //Get last row id
    public int getCurrentID(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {"id","question","answer","remindertime"};
        //Change this query
        Cursor cursor = db.query("questionbank",columns, null, null, null, null, null);
        cursor.moveToLast();
       return Integer.parseInt(cursor.getString(0));
   }

   //find upcoming reminder time (min) ===== change this !!
    public String nextReminder(){

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {"id","question","answer","remindertime"};
        Cursor cursor = db.query("questionbank",columns , null, null, null, null, null);
        cursor.moveToLast();
        String nextRem =  cursor.getString(3);
        return nextRem;


    }
    public String remTimeFromId(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from questionbank where id = ?",new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        Log.d(TAG,"rem time with id"+id+cursor.getString(3));
        String remTime = cursor.getString(3);
        return remTime;

    }

    public int findMinId() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{"id", "question", "answer", "remindertime"};
        Cursor cursor = db.query("questionbank", columns, null, null, null, null, null);
        cursor.moveToFirst();
        int id = Integer.parseInt(cursor.getString(0));
        String min = cursor.getString(3);
        while (!cursor.isLast()) {
            cursor.moveToNext();
            if (compareTime(min, cursor.getString(3)) == true) {
                min = cursor.getString(3);
                id = Integer.parseInt(cursor.getString(0));
          }
      }
      Log.d(TAG,"id of min"+id);
      return id;
    }

    // to compare 2 Time

    public boolean compareTime(String a, String b){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        //Convert frst string to cal
        Date date_a = new Date();
        try {
            date_a = format.parse(a);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal_a = Calendar.getInstance();
        cal_a.setTime(date_a);
        Log.d(TAG,"dateformat ; "+a);

        //Convert second string to cal
        Date date_b = new Date();
        try {
            date_b = format.parse(b);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal_b = Calendar.getInstance();
        cal_b.setTime(date_b);

        //Compare cal_a , cal_b
        if(cal_a.compareTo(cal_b)>0)
        return true;// string_a more recent
        //string_b more recent

        else
            return false;
 }

}