package com.example.roktrajanja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductInfo extends AppCompatActivity {

    TextView mnaziv, mkolicina, mdatum;
    ImageView mslika;
    int mId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        mnaziv = findViewById(R.id.nazivI);
        mkolicina = findViewById(R.id.kolicinaI);
        mdatum = findViewById(R.id.datumI);
        mslika = findViewById(R.id.slika);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String selektor = "SELECT * FROM PROIZVOD WHERE id = "+getIntent().getIntExtra("id",0);
        Cursor c = db.rawQuery(selektor, null);

        if(c.getCount()>0) {
            c.moveToFirst();
            mId = c.getInt(c.getColumnIndex("id"));
            mnaziv.setText(c.getString(c.getColumnIndex("naziv")));
            mdatum.setText(c.getString(c.getColumnIndex("datum")));
            mkolicina.setText(c.getString(c.getColumnIndex("kolicina")));
            byte[] slk = c.getBlob(c.getColumnIndex("slika"));
            mslika.setImageBitmap(BitmapFactory.decodeByteArray(slk,0,slk.length));

        }

        mslika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductInfo.this, "Id: "+getIntent().getIntExtra("id",0), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void izbrisi(View v){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        db.execSQL("delete from PROIZVOD where id ="+mId);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void edit(View v){
        Intent i = new Intent(getApplicationContext(), Add.class);
        i.putExtra("EditId",mId);
        startActivity(i);
        finish();

    }
}
