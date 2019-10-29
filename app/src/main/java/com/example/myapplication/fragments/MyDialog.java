package com.example.myapplication.fragments;

import android.app.Activity;
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

    private OnNameCompletedListener listener;

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
                String first = firstTextInput.getEditText().getText().toString().trim();
                String last = lastTextInput.getEditText().getText().toString().trim();

                if (listener != null) {
                    listener.onNameCompleted(first, last);
                } else if (getTargetFragment() != null) {
                    Intent intent = new Intent();
                    intent.putExtra(FULLNAME_KEY, first + " " + last);
                    getTargetFragment().onActivityResult(
                            getTargetRequestCode(), Activity.RESULT_OK, intent
                    );
                }
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTargetFragment() != null) {
                    getTargetFragment().onActivityResult(
                            getTargetRequestCode(), Activity.RESULT_CANCELED, null
                    );
                }
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setView(view);
        return builder.create();
    }

    public void setListener(OnNameCompletedListener listener) {
        this.listener = listener;
    }

    public interface OnNameCompletedListener {
        void onNameCompleted(String first, String last);
    }
}
