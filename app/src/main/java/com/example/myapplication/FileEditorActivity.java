package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

                File externalFilesDir = getExternalFilesDir(null);
                if (!externalFilesDir.exists()) {
                    externalFilesDir.mkdirs();
                }

                File file = new File(fileName);
                try {
                    OutputStream outputStream = openFileOutput(fileName, MODE_PRIVATE);
                    //new FileOutputStream(file);
                    OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                    writer.write(fileContent);

                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = fileNameEditText.getEditText().getText().toString();

                File file = new File(fileName);
                try {
                    InputStream inputStream = openFileInput(fileName);
                    //new FileInputStream(file);
                    InputStreamReader reader = new InputStreamReader(inputStream);

                    StringBuilder builder = new StringBuilder();

                    int data;
                    while ((data = reader.read()) != -1) {
                        builder.append((char) data);
                    }

                    fileContentEditText.getEditText().setText(builder.toString());

                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
