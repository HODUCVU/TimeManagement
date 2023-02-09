package com.example.quanlythoigian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DayOfHistory extends AppCompatActivity {

    ListView lstPlanOfDay;
    ArrayAdapter<String> adapter;
    List<String> listPlan;
    Intent intent;
    TextView target;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_of_hisroty);
        Initialization();
        ViewPlan();
    }

    private void ViewPlan() {
        //filer plan of day
        String[] columns = {"day", "time", "event","completed"};
        String selection = "day=?";
        String day = intent.getStringExtra("day");
        String[] selectionArgs = {day};
        //View target
        target.setText(MainActivity.getTarget(day));
        Cursor cursor = MainActivity.object.historyDatabase.query(MainActivity.object.nameTableHistory, columns, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            String time = cursor.getString(1);
            String event = cursor.getString(2);
            int completed = cursor.getInt(3);
            if(completed != 0)
                listPlan.add("✔\n\t"+time + "\n\t" + event);
            else
                listPlan.add("✘\n\t"+time + "\n\t" + event);
        }
        cursor.close();
    }

    private void Initialization() {
        listPlan = new ArrayList<>();
        intent = getIntent();
        target = findViewById(R.id.txtTarget);
        lstPlanOfDay = findViewById(R.id.lstPlanOfDayHistory);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listPlan);
        lstPlanOfDay.setAdapter(adapter);
    }
}