package com.example.roktrajanja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

public class Settings extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    Button btn;
    ImageView iv;
    EditText et;
    Switch sw;
    Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final SharedPreferences sharedPreferences = getSharedPreferences("postavke", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        btn = findViewById(R.id.button);
        et = findViewById(R.id.time);
        sw = findViewById(R.id.switch1);
        sp = findViewById(R.id.spinner);


        sw.setChecked(sharedPreferences.getBoolean("switch",true));
        et.setText(sharedPreferences.getString("time",""));
        sp.setSelection(sharedPreferences.getInt("spinner", 0));

        final Context context = this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear().commit();
                editor.putBoolean("switch", sw.isChecked());
                editor.putString("time", et.getText().toString());
                editor.putInt("spinner",sp.getSelectedItemPosition());
                editor.apply();

                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                SQLiteDatabase db = databaseHelper.getReadableDatabase();

                String query = "SELECT * FROM PROIZVOD";
                Cursor c = db.rawQuery(query, null);




                if(sw.isChecked()){
                    if(c.getCount()>0) {
                        while (c.moveToNext()){
                            Intent intent = new Intent(getApplicationContext(), ReminderBroadcast.class);
                            intent.putExtra("notificationId",c.getInt(0));
                            intent.putExtra("todo","Proizvodu "+c.getString(1)+ " ističe rok za "+ sharedPreferences.getInt("spinner",0) +" dana");
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent,0);
                            PomKalendar pk = new PomKalendar(c.getString(3),sharedPreferences.getString("time",""),sharedPreferences.getInt("spinner",0));

                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP,pk.getCalendar().getTimeInMillis(),pendingIntent);
                        }
                    }
                }
                else{
                    if(c.getCount()>0) {
                        while (c.moveToNext()){
                            Intent intent = new Intent(getApplicationContext(), ReminderBroadcast.class);
                            intent.putExtra("notificationId",c.getInt(0));
                            intent.putExtra("todo","Proizvodu "+c.getString(1)+ " ističe rok za "+ sharedPreferences.getInt("spinner",0) +" dana");
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent,0);
                            PomKalendar pk = new PomKalendar(c.getString(3),sharedPreferences.getString("time",""),sharedPreferences.getInt("spinner",0));

                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            alarmManager.cancel(pendingIntent);
                        }
                    }
                }


                finish();
            }
        });

        iv = findViewById(R.id.timePicker);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        et.setText(hourOfDay+":"+minute);
    }

    public void izbrisiBazu(View v){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("DELETE FROM PROIZVOD");
        db.execSQL("delete from sqlite_sequence where name='PROIZVOD'");
        db.close();
        Toast.makeText(this, "Baza očišćena!", Toast.LENGTH_SHORT).show();
    }
}
