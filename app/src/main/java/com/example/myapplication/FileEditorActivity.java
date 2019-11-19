package com.example.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FileEditorActivity extends AppCompatActivity {

    private static final int WRITE_PERM_REQUEST = 20;
    private static final int READ_PERM_REQUEST = 30;

    TextInputLayout fileNameEditText;
    TextInputLayout fileContentEditText;
    Button save;
    Button load;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_editor);

        fileNameEditText = findViewById(R.id.textInputLayout_fileEditor_fileName);
        fileContentEditText = findViewById(R.id.textInputLayout_fileEditor_fileContent);
        save = findViewById(R.id.button_fileEditor_save);
        load = findViewById(R.id.button_fileEditor_load);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = fileNameEditText.getEditText().getText().toString();
                String fileContent = fileContentEditText.getEditText().getText().toString();

                if (ContextCompat.checkSelfPermission(FileEditorActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    writeToFile(fileName, fileContent);
                } else {
                    ActivityCompat.requestPermissions(FileEditorActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_PERM_REQUEST);
                }
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = fileNameEditText.getEditText().getText().toString();

                if (ContextCompat.checkSelfPermission(FileEditorActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    fileContentEditText.getEditText().setText(
                            readFromFile(fileName)
                    );
                } else {
                    ActivityCompat.requestPermissions(FileEditorActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_PERM_REQUEST);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == WRITE_PERM_REQUEST) {
                String fileName = fileNameEditText.getEditText().getText().toString();
                String fileContent = fileContentEditText.getEditText().getText().toString();

                writeToFile(fileName, fileContent);
            } else if (requestCode == READ_PERM_REQUEST) {
                String fileName = fileNameEditText.getEditText().getText().toString();

                fileContentEditText.getEditText().setText(
                        readFromFile(fileName)
                );
            }
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                        this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Why app need this permission")
                    .setMessage("This app wants to access external storage for:\n1.....\n2.....\n3.....")
                    .setPositiveButton("Request Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            ActivityCompat.requestPermissions(FileEditorActivity.this,
                                    permissions,
                                    requestCode);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    private void writeToFile(String fileName, String fileContent) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName);

        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(fileContent);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile(String fileName) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName);

        try {
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(inputStream);

            StringBuilder builder = new StringBuilder();

            int data;
            while ((data = reader.read()) != -1) {
                builder.append((char) data);
            }
            reader.close();

            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
