package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.fragments.MyDialog;
import com.example.myapplication.fragments.MyFragment;
import com.google.android.material.snackbar.Snackbar;

public class FragmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);

        final ConstraintLayout root = findViewById(R.id.constraintLayout_fragments_root);

        MyFragment fragment = new MyFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        //MyDialog myDialog = new MyDialog();
        //myDialog.setListener(new MyDialog.OnNameCompletedListener() {
        //    @Override
        //    public void onNameCompleted(String first, String last) {
        //        String fullName = String.format("Hello %s %s",
        //                first, last);
        //
        //        Snackbar.make(root, fullName, Snackbar.LENGTH_SHORT).show();
        //    }
        //});
        //myDialog.show(fragmentManager, null);
    }
}
