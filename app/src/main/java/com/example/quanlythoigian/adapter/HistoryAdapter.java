package com.example.quanlythoigian.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlythoigian.DayOfHistory;
import com.example.quanlythoigian.MainActivity;
import com.example.quanlythoigian.R;
import com.example.quanlythoigian.model.customAdapterHistory;

public class HistoryAdapter extends ArrayAdapter<customAdapterHistory> {

    Activity context;
    int resource;

    public HistoryAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View customView = inflater.inflate(this.resource,null);

        TextView dayHistory = customView.findViewById(R.id.txtHistoryDay);
        Button deleteDayHistory = customView.findViewById(R.id.btnDeleteHistoryDay);

        customAdapterHistory history = getItem(position);

        dayHistory.setText(history.getDay());
        dayHistory.setOnClickListener(view -> ViewPlanOfDays(history.getDay()));
        deleteDayHistory.setOnClickListener(view -> {
            //delete database of this item
            DeleteDayOfHistory(history.getDay());
            notifyDataSetChanged();
        });
        return customView;
    }
    private void ViewPlanOfDays(String day) {
        Intent intent = new Intent(context,DayOfHistory.class);
        intent.putExtra("day",day);
        context.startActivity(intent);
    }
    private void DeleteDayOfHistory(String day) {
        MainActivity.object.historyDatabase.delete(MainActivity.object.nameTableHistory,"day=?",
                new String[]{day});
    }
}
