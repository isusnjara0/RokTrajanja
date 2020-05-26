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

}
