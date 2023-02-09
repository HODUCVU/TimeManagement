package com.example.quanlythoigian;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.quanlythoigian.adapter.ScheduleAdapter;
import com.example.quanlythoigian.model.customAdapter;

public class Schedule extends AppCompatActivity {

    ListView lst;
    ScheduleAdapter adapter;
    EditText edtTarget;
    public static int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        setTitle("Schedule"); //set title for this layout
        Controls();
        Query();
    }
    public void Query() {
        Cursor cursor = MainActivity.object.calenderDatabase.rawQuery("SELECT * FROM " + MainActivity.object.nameTableCalender, null);
        adapter.clear();
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            String time = cursor.getString(1);
            String event = cursor.getString(2);
            customAdapter c = new customAdapter(time,event);
            adapter.add(c);
        }
        cursor.close();
    }
    private void Controls() {
        edtTarget = findViewById(R.id.edtTarget);
        edtTarget.setText(MainActivity.getTarget(MainActivity.getToday()));
        lst = findViewById(R.id.listSchedule);
        adapter = new ScheduleAdapter(Schedule.this, R.layout.list_items);
        lst.setAdapter(adapter);
    }
    public void ClickAdd(View view) {
        // add extra one event
        Query();
        adapter.add(new customAdapter("",""));
    }
    public void ClickClear(View view) {
        //Clear CalenderDatabase today and in history with today
        ConfirmClear();
    }
    private void ConfirmClear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Clear All plan today").setIcon(R.drawable.icon_calender)
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    MainActivity.object.calenderDatabase.delete(MainActivity.object.nameTableCalender, null, null);
                    MainActivity.object.historyDatabase.delete(MainActivity.object.nameTableHistory,"day=?",new String[]{MainActivity.getToday()});
                    MainActivity.object.targetDatabase.delete(MainActivity.object.nameTableTarget,"day=?",new String[]{MainActivity.getToday()});
                    Query();
                    edtTarget.setText(MainActivity.getTarget(MainActivity.getToday()));
                    Toast.makeText(Schedule.this, "Deleted plan today", Toast.LENGTH_SHORT).show();
                })
                .create()
                .show();
    }
    public void ClickSave(View view) {
        History.InsertPlanTodayIntoHistory();
        String target = edtTarget.getText().toString();
        if(!target.isEmpty()) {
            InsertTarget(MainActivity.getToday(), target);
            finish();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Target is empty").setIcon(R.drawable.icon_target)
                    .setPositiveButton("OK", (dialogInterface, i) -> {})
                    .create()
                    .show();
        }
    }
    private void InsertTarget(String day,String target) {
        //B1: delete target in today
        //B2: add target
        MainActivity.object.targetDatabase.delete(MainActivity.object.nameTableTarget,"day=?",new String[]{day});
        ContentValues values = new ContentValues();
        values.put("day", day);
        values.put("target",target);
        MainActivity.object.targetDatabase.insert(MainActivity.object.nameTableTarget,null,values);
    }
}