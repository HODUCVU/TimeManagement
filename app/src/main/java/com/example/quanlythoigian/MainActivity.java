package com.example.quanlythoigian;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.quanlythoigian.database.AddSQLiteFromAssets;
import com.example.quanlythoigian.object.ObjectMain;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    Toast t;
    Intent intent;
    AddSQLiteFromAssets db;
    public static ObjectMain object;
    //public static String databaseName = "QuanLyThoiGian.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //read file assets
        db = new AddSQLiteFromAssets(this);
        db.processCopy();
        object = new ObjectMain();
        //createDatabase
        object.calenderDatabase = openOrCreateDatabase(object.databaseName, MODE_PRIVATE,null);
        object.historyDatabase = openOrCreateDatabase(object.databaseName, MODE_PRIVATE, null);
        object.targetDatabase = openOrCreateDatabase(object.databaseName, MODE_PRIVATE,null);
        object.sharedPreferences = getSharedPreferences("last scheduled date", MODE_PRIVATE);
        checkDay();
    }
    public static String getToday() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day + "/" + month + "/" + year;
    }
    private void checkDay() {
        Cursor cursorHistory = MainActivity.object.historyDatabase.rawQuery("SELECT * FROM " + MainActivity.object.nameTableHistory,null);
        String lastScheduledDate = "";
        while (cursorHistory.moveToNext()) {
            lastScheduledDate = cursorHistory.getString(0);
        }
        cursorHistory.close();
        //if today is not schedule then calender is empty
        if(!lastScheduledDate.equals(getToday())) {
            //delete calender database
            MainActivity.object.calenderDatabase.delete(MainActivity.object.nameTableCalender, null, null);
        }
            createNotification(getTarget(MainActivity.getToday()));

    }
    public void ClickSchedule(View view) {
        t = Toast.makeText(MainActivity.this, "Schedule",Toast.LENGTH_SHORT);
        t.show();
        intent = new Intent(this,Schedule.class);
        startActivity(intent);
    }

    public void ClickCalender(View view) {
        t = Toast.makeText(MainActivity.this, "Calender",Toast.LENGTH_SHORT);
        t.show();
        intent = new Intent(this,Calender.class);
        startActivity(intent);
    }

    public void ClickHistory(View view) {
        t = Toast.makeText(MainActivity.this, "History",Toast.LENGTH_SHORT);
        t.show();
        intent = new Intent(this,History.class);
        startActivity(intent);
    }
    public static String getTarget(String day) {
        String t = "";
        String[] columns = {"day", "target"};
        String selection = "day=?";
        String[] selectionArgs = {day};
        Cursor cursor = MainActivity.object.targetDatabase.query(MainActivity.object.nameTableTarget,
                columns, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext())
            t = cursor.getString(1);
        cursor.close();
        return t;
    }
    private void createNotification(String content) {
        try {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String channelId = "channel_id_01";
            CharSequence channelName = "Example Channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(notificationChannel);
            }

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);

            Notification notification = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                notification = new Notification.Builder(this, channelId)
                        .setContentTitle("Target today")
                        .setContentText(content)
                        .setSmallIcon(R.drawable.icon_calender)
                        .setContentIntent(pendingIntent)
                        .build();
            }

            notificationManager.notify(1, notification);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("Error: ", e.toString());
        }
    }
}