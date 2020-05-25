package com.example.roktrajanja;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        final long [] pattern = {0,500,100,500};
        String CHANNEL_ID="1234";
        int id = intent.getIntExtra("notificationId",0);
        String description = intent.getStringExtra("todo");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = new NotificationChannel(CHANNEL_ID,"proizvod",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setLightColor(Color.WHITE);
            notificationChannel.setVibrationPattern(pattern);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);

            if(notificationManager != null){

            }
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.groceries_icon)
                .setContentTitle("Obavjest za proizvod")
                .setContentText(description)
                .setVibrate(pattern)
                //.setDefaults(Notification)
                .setColor(Color.WHITE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);



        notificationManager.notify(id,builder.build());
    }
}
