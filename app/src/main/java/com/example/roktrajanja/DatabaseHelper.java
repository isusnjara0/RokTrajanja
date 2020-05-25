package com.example.roktrajanja;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "RokTrajanja.dp";
    public static final String TABLE1 = "PROIZVOD";
    public static final String ID = "id";
    public static final String NAZIV = "naziv";
    public static final String KOLICINA = "kolicina";
    public static final String DATUM = "datum";
    public static final String SLIKA = "slika";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PROIZVOD = "CREATE TABLE PROIZVOD ("
                + ID + " integer primary key autoincrement, "
                + NAZIV + " text not null, "
                + KOLICINA + " numeric not null, "
                + DATUM + " date not null, "
                + SLIKA + " blob not null)";

        db.execSQL(CREATE_TABLE_PROIZVOD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE1);
    }

    //insert an image
  /*  public Boolean insertImage(String x, int i){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            FileInputStream fs = new FileInputStream(x);
            byte[] imgByte = new byte[fs.available()];
            fs.read(imgByte);
            ContentValues contentValues = new ContentValues();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }*/
}
