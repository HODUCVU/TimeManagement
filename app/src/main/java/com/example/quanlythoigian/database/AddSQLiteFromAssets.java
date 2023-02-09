package com.example.quanlythoigian.database;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddSQLiteFromAssets {
    private final Activity context;
    private final String DatabaseName = "QuanLyThoiGian.db";
    private final String DB_PATH = "/databases/";

    public AddSQLiteFromAssets(Activity context){
        this.context = context;
    }
    public void processCopy() {
        File dbFile = context.getDatabasePath(DatabaseName);
        if(!dbFile.exists()) {
            try {
                CopyDataBaseFromAssets();
                Toast.makeText(context, "Copy database success", Toast.LENGTH_LONG).show();
            }
            catch (Exception e) {
                Toast.makeText(context, "Error: " + e, Toast.LENGTH_LONG).show();
                Log.e("Error",e.toString());
            }
        }
    }

    private void CopyDataBaseFromAssets() throws IOException {
        InputStream input = context.getAssets().open(DatabaseName);
        String outFile = getDatabasePath();
        File file = new File(context.getApplicationInfo().dataDir + DB_PATH);
        if(!file.exists()) {
            file.mkdir();
        }
        OutputStream output = new FileOutputStream(outFile);
        byte[] buffer = new byte[1024];
        int length;
        while((length = input.read(buffer) )> 0) {
            output.write(buffer,0,length);
        }
        output.flush();
        output.close();
        input.close();
    }

    private String getDatabasePath() {
        return context.getApplicationInfo().dataDir + DB_PATH + DatabaseName;
    }
}
