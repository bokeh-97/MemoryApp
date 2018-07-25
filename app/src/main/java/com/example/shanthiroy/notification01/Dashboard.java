package com.example.shanthiroy.notification01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        GridView dashboard_gridview = (GridView)findViewById(R.id.dashboard_gridview);
        DBHelper mDBHelper = new DBHelper(this);
        ArrayList<Reminder_item> reminder_items = mDBHelper.getAllData();
        ReminderAdapter reminderAdapter = new ReminderAdapter(reminder_items,this);
        dashboard_gridview.setAdapter(reminderAdapter);

    }
}
