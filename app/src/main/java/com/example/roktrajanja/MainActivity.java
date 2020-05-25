package com.example.roktrajanja;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void dodaj(View v){
        Intent i = new Intent(getApplicationContext(),Add.class);
        startActivity(i);
    }

    public void list(View v){
        Intent i = new Intent(getApplicationContext(),List.class);
        startActivity(i);
    }

    public void settings(View v){
        Intent i = new Intent(getApplicationContext(),Settings.class);
        startActivity(i);
    }

    public void notification(View v){
        Toast.makeText(this, "Podsjetnik podešen!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), ReminderBroadcast.class);
        intent.putExtra("notificationId",1);
        intent.putExtra("todo","proizvod kokoš istice za 10 dana");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent,0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long timeAtButtonClick = System.currentTimeMillis();

        long tenSeconds = 1000 * 10;

        alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick +  tenSeconds, pendingIntent);

    }

}
