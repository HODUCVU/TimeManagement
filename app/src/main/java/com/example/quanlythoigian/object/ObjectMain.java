package com.example.quanlythoigian.object;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

public class ObjectMain {
    public String nameTableCalender = "Calender";
    public String nameTableHistory = "History";
    public String nameTableTarget = "Target";
    public SQLiteDatabase calenderDatabase = null;
    public SQLiteDatabase historyDatabase = null;
    public SQLiteDatabase targetDatabase = null;
    public String databaseName = "QuanLyThoiGian.db";
    public SharedPreferences sharedPreferences;

}
