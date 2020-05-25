package com.example.roktrajanja;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Add extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText mnaziv, mdatum, mkolicina;
    ImageView iv, mslika;
    Spinner sp;
    Uri imageUri;
    int mId;
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

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        if(getIntent().getIntExtra("EditId",0)!=0){
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
        ContentValues korisnikValues = new ContentValues();
        korisnikValues.put("naziv", mnaziv.getText().toString());
        korisnikValues.put("datum", mdatum.getText().toString());
        korisnikValues.put("kolicina", mkolicina.getText().toString()+" "+sp.getSelectedItem().toString());
      //  korisnikValues.put("slika", mslika.getPath);

        if(!mnaziv.getText().toString().equals("") && !mdatum.getText().toString().equals("") && !mkolicina.getText().toString().equals("")){
            db.insert("PROIZVOD", null, korisnikValues);
            Toast.makeText(this, "Proizvod dodan!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Proizvod nije dodan!", Toast.LENGTH_SHORT).show();
        }
        // insertiraj redak
        // prekini konekciju na bazu
        db.close();
        finish();
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
