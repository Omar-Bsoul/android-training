package com.example.myapplication.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerDialogFragment extends DialogFragment {

    private final TimePickerDialog.OnTimeSetListener listener;

    public TimePickerDialogFragment(TimePickerDialog.OnTimeSetListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(), listener,
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                false
        );

        return timePickerDialog;
    }
}
