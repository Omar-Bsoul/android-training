package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class CameraActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 70;

    ImageView cameraImageView;
    Button cameraButton;

    Uri imageUri; // = content://appId.provider/....................

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraImageView = findViewById(R.id.imageView_camera);
        cameraButton = findViewById(R.id.button_camera);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File file = getImageFile(getPicturePrefix(), ".jpg");
                    imageUri = FileProvider.getUriForFile(CameraActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);

                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    cameraImageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Could capture an image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File getImageFile(String name, String ext) throws IOException {
        File picturesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (!picturesDir.exists()) {
            picturesDir.mkdirs();
        }

        return File.createTempFile(name, ext, picturesDir);
    }

    private String getPicturePrefix() {
        Calendar calendar = Calendar.getInstance();

        return String.format("PIC %s-%s-%s-",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }
}
