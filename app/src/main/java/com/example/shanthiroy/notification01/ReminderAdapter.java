package com.example.shanthiroy.notification01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
            holder.reminder_set = (TextView)convertView.findViewById(R.id._reminder_set);


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        Reminder_item rem_item = reminder_list.get(position);
        holder.id.setText(rem_item.getId());
        holder.question.setText(rem_item.getQuestion());
        holder.answer.setText(rem_item.getAnswer());

        holder.reminder_set.setText(rem_item.getReminderSet());


        return convertView;
    }

    private static class ViewHolder{
        public TextView id;
        public TextView question;
        public TextView answer;
        public TextView reminder_set;

    }
}