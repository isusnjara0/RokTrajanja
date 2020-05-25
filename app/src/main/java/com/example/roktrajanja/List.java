package com.example.roktrajanja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class List extends AppCompatActivity {

    ListView listView;
    ArrayList<Proizvod> listItem;
    String mId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String query = "SELECT * FROM PROIZVOD";
        Cursor c = db.rawQuery(query, null);

        listItem = new ArrayList<>();

        if(c.getCount()>0) {
            while (c.moveToNext()){
                listItem.add(new Proizvod(R.drawable.add,c.getString(1),"2 dana"));
                MyCustomAdapter mAdaper = new MyCustomAdapter(getApplicationContext(), R.layout.my_listview_item , listItem);

                listView.setAdapter(mAdaper);



            }}else{

        c.close();
        db.close();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }

        });






}
}}
