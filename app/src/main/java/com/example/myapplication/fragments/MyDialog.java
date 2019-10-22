package com.example.myapplication.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputLayout;

public class MyDialog extends DialogFragment {
    public static final String FULLNAME_KEY = "FULLNAME_KEY";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View view = inflater.inflate(R.layout.custom_dialog, null, false);

        final TextInputLayout firstTextInput = view.findViewById(R.id.textInputLayout_dialog_firstName);
        final TextInputLayout lastTextInput = view.findViewById(R.id.textInputLayout_dialog_lastName);
        Button okButton = view.findViewById(R.id.button_dialog_ok);
        Button cancelButton = view.findViewById(R.id.button_dialog_cancel);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = String.format("Hello %s %s",
                        firstTextInput.getEditText().getText().toString().trim(),
                        lastTextInput.getEditText().getText().toString().trim());

                Intent intent = new Intent();
                intent.putExtra(FULLNAME_KEY, fullName);

                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setView(view);
        return builder.create();
    }

}
