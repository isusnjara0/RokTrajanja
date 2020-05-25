package com.example.roktrajanja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Settings extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    Button btn;
    ImageView iv;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn = findViewById(R.id.button);
        et = findViewById(R.id.time);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
