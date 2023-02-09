package com.example.quanlythoigian;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quanlythoigian.adapter.CalenderAdapter;
import com.example.quanlythoigian.model.customAdapterCalender;


public class Calender extends AppCompatActivity {

    ListView lstCalender;
    CalenderAdapter adapter;

    TextView target;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        setTitle("Calender - " + MainActivity.getToday());
        Initialization();
        target.setText(MainActivity.getTarget(MainActivity.getToday()));
        Query();
    }

    @Override
    protected void onResume() {
        super.onResume();
        target.setText(MainActivity.getTarget(MainActivity.getToday()));
        Query();
    }
    private void Query() {
        Cursor cursor = MainActivity.object.calenderDatabase.rawQuery("SELECT * FROM " + MainActivity.object.nameTableCalender, null);
        adapter.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String time = cursor.getString(1);
            String event = cursor.getString(2);
            int completed = cursor.getInt(3);
            customAdapterCalender c = new customAdapterCalender(id,time,event,completed);
            adapter.add(c);
        }
        cursor.close();
    }
    private void Initialization() {
        target = findViewById(R.id.target);
        lstCalender = findViewById(R.id.lstCalender);
        adapter = new CalenderAdapter(Calender.this,R.layout.list_items_calender);
        lstCalender.setAdapter(adapter);
    }

}