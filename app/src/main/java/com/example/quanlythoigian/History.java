package com.example.quanlythoigian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import com.example.quanlythoigian.adapter.HistoryAdapter;
import com.example.quanlythoigian.model.customAdapterHistory;

public class History extends AppCompatActivity {

    ListView lstHistory;
    HistoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setTitle("History");
        Initialization();
        Query();
    }

    private void Query() {
        Cursor cursor = MainActivity.object.historyDatabase.rawQuery("SELECT * FROM " + MainActivity.object.nameTableHistory,null);
        adapter.clear();
        String[] day = new String[]{"",""};
        while (cursor.moveToNext()) {
            day[1] = cursor.getString(0);
            String time = cursor.getString(1);
            String event = cursor.getString(2);
            int complete = cursor.getInt(3);
            customAdapterHistory c = new customAdapterHistory(day[1],time,event,complete);
            //prevent repeat day in history
            if(!day[0].equals(day[1]))
                adapter.add(c);
            day[0] = day[1];
        }
        cursor.close();
    }
    private void Initialization() {
        lstHistory = findViewById(R.id.lstHistoryDay);
        adapter = new HistoryAdapter(this,R.layout.list_item_history);
        lstHistory.setAdapter(adapter);
    }
    public static void InsertPlanTodayIntoHistory() {
        //B0: delete data of today in History
        //B1: get today
        //B2: extract data from Calender
        //B3: including data into History with today is key
        MainActivity.object.historyDatabase.delete(MainActivity.object.nameTableHistory,"day=?",new String[]{MainActivity.getToday()});
        ContentValues values = new ContentValues();
        Cursor cursor = MainActivity.object.calenderDatabase.rawQuery("SELECT * FROM " + MainActivity.object.nameTableCalender, null);
        while (cursor.moveToNext()) {
           String time = cursor.getString(1);
           String event = cursor.getString(2);
           int completed = cursor.getInt(3);
           values.put("day",MainActivity.getToday());
           values.put("time",time);
           values.put("event",event);
           values.put("completed",completed);
           try {
               MainActivity.object.historyDatabase.insert(MainActivity.object.nameTableHistory,null,values);
           }
           catch (Exception e) {
               Log.e("Error: ", e.toString());
           }
        }
        cursor.close();
    }
}