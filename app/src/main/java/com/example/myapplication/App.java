package com.example.myapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class App extends Application {

    public static final String NOTIFICATION_CHANNEL_1 = "ID 1";
    private NotificationManagerCompat notiifcationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        notiifcationManager = NotificationManagerCompat.from(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(NOTIFICATION_CHANNEL_1, "New Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);

            notiifcationManager.createNotificationChannel(channel);
        }
    }
}
