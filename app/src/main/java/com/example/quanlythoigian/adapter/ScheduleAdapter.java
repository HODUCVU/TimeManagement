package com.example.quanlythoigian.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlythoigian.MainActivity;
import com.example.quanlythoigian.R;
import com.example.quanlythoigian.Schedule;
import com.example.quanlythoigian.model.customAdapter;


public class ScheduleAdapter extends ArrayAdapter<customAdapter> {
    private final Activity context;
    private final int resource;
    public ScheduleAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context; //Activity
        this.resource = resource; //list_items
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View custom= inflater.inflate(this.resource,null);
        //Khai báo đối tượng: TextView, Button, ImageView
        EditText time = custom.findViewById(R.id.edtTime);
        EditText event = custom.findViewById(R.id.edtEvent);
        Button save = custom.findViewById(R.id.btnSaveItem);
        customAdapter schedule = getItem(position);
        //Sử dụng
        time.setText(schedule.getTime());
        event.setText(schedule.getEvent());
        save.setOnClickListener(view -> {
            String t = time.getText().toString();
            String e = event.getText().toString();
            int completed = 0;
            Insert(t,e,completed);
        });
        return custom;
    }
    private void Insert(String t, String e, int completed) {
        Schedule.id++;
        ContentValues values = new ContentValues();
        //values.put(column,value)
        values.put("id", Schedule.id);
        values.put("time",t);
        values.put("event",e);
        values.put("completed",completed);
        //insert into Calender table
        try {
            MainActivity.object.calenderDatabase.insert(MainActivity.object.nameTableCalender, null,
                    values);
           // Schedule.id++;  //When database is added, id will increase
            Toast.makeText(context,"Success Insert\n" + t + " - " + e, Toast.LENGTH_SHORT).show();
        }
        catch (Exception exception)
        {
            Toast.makeText(context,"Fail Insert: " + exception, Toast.LENGTH_SHORT).show();
            Log.e("Error",exception.toString());
        }
    }
}
