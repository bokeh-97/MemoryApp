package com.example.shanthiroy.notification01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
       cal.add(Calendar.MINUTE,1);
        String first_rem = dateformat.format(cal.getTime());
        Log.d(TAG,"first rem:"+first_rem);

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("question", mquestion);
        values.put("answer", manswer);
        values.put("remindertime",first_rem);

// Insert the new row, returning the primary key value of the new row
       db.insert("questionbank", null, values);

        return true;
    }




//Get remindertime from id

    public String remTimeFromId(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from questionbank where id = ?",new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        Log.d(TAG,"rem time with id : "+id+"  remtime  "+cursor.getString(3));
        String remTime = cursor.getString(3);
        cursor.close();
        return remTime;

    }

    //Get number of rows in the table

    public int getRowCount (){
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT count(*) FROM questionbank";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        Log.d(TAG,"row count"+icount);
        return icount;
 }
    //Get question from id
    public String getQuestion(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from questionbank where id = ?",new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        Log.d(TAG,"question with "+id+cursor.getString(1));

        String currentQuestion = cursor.getString(1);
        cursor.close();
        return currentQuestion;

 }

 // Get all rows ; store into ArrayList<Reminder_item>

    public ArrayList<Reminder_item> getAllData(){
        ArrayList<Reminder_item> reminder_items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from questionbank",null);
        while(res.moveToNext()) {
            String id = res.getString(0).toString();
            String question = res.getString(1).toString();
            String answer = res.getString(2).toString();
            String reminder_set = res.getString(3).toString();
            Reminder_item rem_item = new Reminder_item(id, question, answer, reminder_set);
            reminder_items.add(rem_item);
        }

        return reminder_items;
        }




 //Get answer from id
 public String getAnswer(int id){

     SQLiteDatabase db = this.getReadableDatabase();
     Cursor cursor = db.rawQuery("Select * from questionbank where id = ?",new String[]{String.valueOf(id)});
     cursor.moveToFirst();
     Log.d(TAG,"answer with "+id+cursor.getString(2));

     String currentAnswer = cursor.getString(2);
     Log.d(TAG,"currentAnswer):"+ currentAnswer);
     cursor.close();
     return currentAnswer;
 }

 //Get last row id
    public int getLastId(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from questionbank",null);
        cursor.moveToLast();
        return Integer.parseInt(cursor.getString(0));
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


 // Update reminder time
    public boolean updateRemtime(int id, int dur){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE,dur);
        String next_rem = dateformat.format(cal.getTime());

        ContentValues values = new ContentValues();
        values.put("remindertime", next_rem);


        db.update("questionbank",values,"id="+id,null);
        Log.d(TAG,"after update , qn :"+getQuestion(id));
        Log.d(TAG,"after update , ans :"+getAnswer(id));
        Log.d(TAG,"after update , rem time :"+ next_rem);


        return true;

    }


}