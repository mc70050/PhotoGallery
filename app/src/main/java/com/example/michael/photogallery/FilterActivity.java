package com.example.michael.photogallery;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

public class FilterActivity extends AppCompatActivity {

    private EditText fromDate;
    private EditText toDate;
    private EditText fromLoc;
    private EditText toLoc;
    private Calendar fromCalendar;
    private Calendar toCalendar;
    private DatePickerDialog.OnDateSetListener fromListener;
    private DatePickerDialog.OnDateSetListener toListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        fromDate = (EditText) findViewById(R.id.timeframe_from_text);
        toDate   = (EditText) findViewById(R.id.timeframe_to_text);
        fromLoc = (EditText) findViewById(R.id.location_from_text);
        toLoc = (EditText) findViewById(R.id.location_to_text);
    }

    public void goBack(View v) {
        this.finish();
    }

    public void search(View v) {
        Intent i = new Intent();
        i.putExtra("STARTDATE", getDate(fromDate.getText().toString()));
        i.putExtra("ENDDATE", getDate(toDate.getText().toString()));
        i.putExtra("STARTLOC", getLocation(fromLoc.getText().toString()));
        i.putExtra("ENDLOC", getLocation(toLoc.getText().toString()));
        setResult(RESULT_OK, i);
        finish();
    }

    private String getDate(String date) {
        if (date.length() == 0) {
            return "";
        }
        String temp = "";
        String[] dates = date.split("/");
        for (int i = 0; i < dates.length; i++) {
            temp += dates[i];
        }
        Log.d("Date is: ",temp);
        return temp;
    }

    private double[] getLocation(String loc) {
        String[] coords = loc.split(",");
        double[] locs = new double[2];
        for (int x = 0; x < coords.length; x++) {
            if (coords[x].length() != 0)
                locs[x] = Double.parseDouble(coords[x]);
            else
                locs[x] = 0;
        }
        return locs;
    }
}
