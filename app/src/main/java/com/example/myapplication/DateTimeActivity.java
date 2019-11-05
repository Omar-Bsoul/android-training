package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myapplication.fragments.DatePickerDialogFragment;
import com.example.myapplication.fragments.TimePickerDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);

        final ConstraintLayout root = findViewById(R.id.constraintLayout_fragments_root);
        textView = findViewById(R.id.textView_dateTime_date);

        showDatePicker();
        showTimePicker();
    }

    void showDatePicker() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                Date date = calendar.getTime();

                textView.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
            }
        };

        DatePickerDialogFragment dialogFragment = new DatePickerDialogFragment(onDateSetListener);
        dialogFragment.show(getSupportFragmentManager(), null);
    }

    void showTimePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(0, 0, 0, hourOfDay, minute);
                Date date = calendar.getTime();

                textView.setText(new SimpleDateFormat("hh:mm a").format(date));
            }
        };

        TimePickerDialogFragment timePickerDialogFragment = new TimePickerDialogFragment(onTimeSetListener);
        timePickerDialogFragment.show(getSupportFragmentManager(), null);
    }
}
