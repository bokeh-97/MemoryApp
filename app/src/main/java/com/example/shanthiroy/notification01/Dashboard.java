package com.example.shanthiroy.notification01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    GridView dashboard_gridview;
    static Dashboard dashboard_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setGridView();

        dashboard_activity = this;


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Dashboard.this, MainActivity.class));
        finish();

    }
    public static Dashboard getInstance(){
        return dashboard_activity;

    }


    public void setGridView(){
        dashboard_gridview = (GridView) findViewById(R.id.dashboard_gridview);

        final DBHelper mDBHelper = new DBHelper(this);
        ArrayList<Reminder_item> reminder_items = mDBHelper.getAllData();
        ReminderAdapter reminderAdapter = new ReminderAdapter(reminder_items, this);
        dashboard_gridview.setAdapter(reminderAdapter);




    }
}
