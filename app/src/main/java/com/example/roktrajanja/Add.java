package com.example.roktrajanja;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Add extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText mnaziv, mdatum, mkolicina;
    ImageView iv, mslika;
    Spinner sp;
    Uri imageUri;
    int mId;
    long id;
    boolean edt;
    public static final int REQUEST_CAMERA = 101;
    public static final int PICK_IMAGE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mnaziv = findViewById(R.id.naziv);
        mdatum = findViewById(R.id.datum);
        mkolicina = findViewById(R.id.kolicina);
        mslika = findViewById(R.id.slikaImg);
        iv = findViewById(R.id.datePicker);
        sp = findViewById(R.id.spinner2);

        mslika.setImageResource(R.drawable.def);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        if(edt=(getIntent().getIntExtra("EditId",0)!=0)){
            mId = getIntent().getIntExtra("EditId",0);
            getData();
        }

    }

    public void ponisti(View v){
        mnaziv.setText("");
        mdatum.setText("");
        mkolicina.setText("");
    }

    public void add(View v){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        //pretvaranje slike u byte
        Bitmap bitmap = ((BitmapDrawable) mslika.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();

        ContentValues korisnikValues = new ContentValues();
        korisnikValues.put("naziv", mnaziv.getText().toString());
        korisnikValues.put("datum", mdatum.getText().toString());
        korisnikValues.put("kolicina", mkolicina.getText().toString()+" "+sp.getSelectedItem().toString());
        korisnikValues.put("slika", imageInByte);

        if(!mnaziv.getText().toString().equals("") && !mdatum.getText().toString().equals("") && !mkolicina.getText().toString().equals("")){
            if(edt){
                String[] whereArgs = new String[]{String.valueOf(mId)};
                id = db.update("PROIZVOD",korisnikValues,"id=?",whereArgs);
                Toast.makeText(this, "Proizvod uređen!", Toast.LENGTH_SHORT).show();
            }
            else{
                id = db.insert("PROIZVOD", null, korisnikValues);
                Toast.makeText(this, "Proizvod dodan!", Toast.LENGTH_SHORT).show();
            }


        }
        else{
            Toast.makeText(this, "Proizvod nije dodan!", Toast.LENGTH_SHORT).show();
        }
        // insertiraj redak
        // prekini konekciju na bazu


        db.close();

        //alarm
        notification();


        finish();
    }

    private void notification() {
        final SharedPreferences sharedPreferences = getSharedPreferences("postavke", MODE_PRIVATE);


        Intent intent = new Intent(getApplicationContext(), ReminderBroadcast.class);
        intent.putExtra("notificationId",id);
        intent.putExtra("todo","Proizvodu "+mnaziv.getText().toString()+ " istječe rok za "+ sharedPreferences.getInt("spinner",0) +" dana");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        PomKalendar pk = new PomKalendar(mdatum.getText().toString(), sharedPreferences.getString("time",""), sharedPreferences.getInt("spinner", 0));

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,pk.getCalendar().getTimeInMillis(),pendingIntent);
    }

    public void update(View v){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String currentDateString = timeStampFormat.format(c.getTime());
        mdatum.setText(currentDateString);
    }

    public void openGallery(View v){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public void openCamera(View v){
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, REQUEST_CAMERA);
    }

    /*public String getPath(Uri uri){
        if(uri==null)return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor =
    }*/

    public void getData(){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM PROIZVOD WHERE id ="+mId,null);
        if(c.getCount()>0) {
            c.moveToFirst();
            mnaziv.setText(c.getString(c.getColumnIndex("naziv")));
            mdatum.setText(c.getString(c.getColumnIndex("datum")));
            mkolicina.setText(c.getString(c.getColumnIndex("kolicina")));
        }
        db.close();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CAMERA){
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                mslika.setImageBitmap(bmp);
            }
            else if(requestCode == PICK_IMAGE){
                imageUri = data.getData();
                mslika.setImageURI(imageUri);
            }

        }
    }
}
