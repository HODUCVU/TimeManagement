package com.example.quanlythoigian.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.quanlythoigian.MainActivity;
import com.example.quanlythoigian.R;
import com.example.quanlythoigian.model.customAdapterCalender;

public class CalenderAdapter extends ArrayAdapter<customAdapterCalender> {

    @NonNull Activity context;
    int resource;

    public CalenderAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = this.context.getLayoutInflater();
        View custom = inflater.inflate(this.resource,null);

        CheckedTextView checkCompleted = custom.findViewById(R.id.checkComplete);

        customAdapterCalender calender = getItem(position);

        checkCompleted.setText(calender.toString());
        checkCompleted.setChecked(calender.isCompleted());
        checkCompleted.setOnClickListener(view -> {
            checkCompleted.toggle();
            UpdateCheckCompleted(checkCompleted.isChecked(), calender.getId());
            UpdateCheckCompletedHistory(checkCompleted.isChecked(), MainActivity.getToday(),calender.getEvent());
        }
        );
        checkCompleted.setOnLongClickListener(view -> {
            DeleteEvent(MainActivity.getToday(),calender.getEvent());
            notifyDataSetChanged();
            return false;
        });
        return custom;
    }
    private void DeleteEvent(String day, String event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Clear this event").setIcon(R.drawable.icon_calender)
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    MainActivity.object.calenderDatabase.delete(MainActivity.object.nameTableCalender, "event=?", new String[]{event});
                    String[] whereArgs = new String[]{day, event};
                    MainActivity.object.historyDatabase.delete(MainActivity.object.nameTableHistory, "day=? and event=?", whereArgs);
                    Toast.makeText(context, "Deleted event", Toast.LENGTH_SHORT).show();
                })
                .create()
                .show();
    }
    private void UpdateCheckCompleted(boolean completed, int id) {
        int check;
        if(completed) check = 1;
        else check = 0;
        ContentValues values = new ContentValues();
        values.put("completed",check);
        long result = MainActivity.object.calenderDatabase.update(MainActivity.object.nameTableCalender,values,
                "id=?",new String[]{id+""});
        if(result>0) Toast.makeText(context, "Success update", Toast.LENGTH_SHORT).show();
        else  Toast.makeText(context, "fail update", Toast.LENGTH_SHORT).show();
    }
    private void UpdateCheckCompletedHistory(boolean completed,String day, String event) {
        int check;
        if(completed) check = 1;
        else check = 0;
        ContentValues values = new ContentValues();
        values.put("completed",check);
        //Condition
        String[] whereArg = {day, event};
        MainActivity.object.historyDatabase.update(MainActivity.object.nameTableHistory,values,
                "day=? and event=?",whereArg);
    }
}
