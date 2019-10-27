package com.example.myapplication.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;

public class MyFragment extends Fragment {
    private static final int NAME_REQUEST_CODE = 2837;

    LinearLayout root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return  super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        root = view.findViewById(R.id.root);

        TextView textView = view.findViewById(R.id.textView);
        textView.setText("Text Changed");

        Button showDialog = view.findViewById(R.id.button_dialog);
        showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog dialog = new MyDialog();
                dialog.setRequestCode(NAME_REQUEST_CODE);
                dialog.show(getChildFragmentManager(), null);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NAME_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String fullName = data.getStringExtra(MyDialog.FULLNAME_KEY);
                Snackbar.make(root, fullName, Snackbar.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Snackbar.make(root, "You canceled the dialog", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
