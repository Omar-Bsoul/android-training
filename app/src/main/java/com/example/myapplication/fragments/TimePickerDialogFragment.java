package com.example.myapplication.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TimePickerDialogFragment extends TimePickerDialog {

    private final OnTimeSetListener listener;
    private final int hourOfDay;
    private final int minute;

    public TimePickerDialogFragment(Context context, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
        super(context, listener, hourOfDay, minute, is24HourView);
        this.listener = listener;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        super.onTimeChanged(view, hourOfDay, minute);

        if (listener != null) {
            listener.onTimeSet(view, hourOfDay, minute);
        }
    }
}
