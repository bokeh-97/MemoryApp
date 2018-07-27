package com.example.shanthiroy.notification01;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;


public class ReminderAdapter extends BaseAdapter {

    private ArrayList<Reminder_item> reminder_list;
    private Context context;

    public ReminderAdapter(ArrayList<Reminder_item> list, Context cont){
        this.reminder_list = list;
        this.context = cont;
    }

    @Override
    public int getCount() {
        return this.reminder_list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.reminder_list.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null){
            LayoutInflater inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.listview_item, null);

            holder = new ViewHolder();
            holder.id = (TextView)convertView.findViewById(R.id._id);
            holder.question = (TextView)convertView.findViewById(R.id._question);
            holder.answer = (TextView)convertView.findViewById(R.id._answer);
            holder.reminder_delete = (Button) convertView.findViewById(R.id.reminder_delete);





            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        final Reminder_item rem_item = reminder_list.get(position);
        holder.id.setText(rem_item.getId());
        holder.question.setText(rem_item.getQuestion());
        holder.answer.setText(rem_item.getAnswer());
        holder.reminder_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               DBHelper mDBHelper  = new DBHelper(context);
               mDBHelper.deleteReminder(Integer.parseInt(rem_item.getId()));
                Intent notifyIntent_N = new Intent(context,MyReceiver.class);

                PendingIntent pendingIntent_N = PendingIntent.getService
                        (context, Integer.parseInt(rem_item.getId())+1000, notifyIntent_N, PendingIntent.FLAG_UPDATE_CURRENT);


                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                try{alarmManager.cancel(pendingIntent_N);}
                catch (Exception e) {

                }
             }
        });







        return convertView;
    }

    private static class ViewHolder{
        public TextView id;
        public TextView question;
        public TextView answer;
        public Button reminder_delete;



    }
}