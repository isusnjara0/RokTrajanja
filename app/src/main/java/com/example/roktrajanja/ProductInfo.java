package com.example.roktrajanja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
           // mslika.setImageURI(c.getString(c.getColumnIndex("slika")));

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
        finish();
    }

    public void edit(View v){
        Intent i = new Intent(getApplicationContext(), Add.class);
        i.putExtra("EditId",mId);
        startActivity(i);

    }
}
