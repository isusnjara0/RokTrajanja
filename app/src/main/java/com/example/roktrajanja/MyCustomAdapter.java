package com.example.roktrajanja;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MyCustomAdapter extends ArrayAdapter<Proizvod> {
    Context mCtx;
    int resource;
    ArrayList<Proizvod> mList;


    public MyCustomAdapter(Context mCtx, int resource, ArrayList<Proizvod> mlist){
        super(mCtx, resource,mlist);
        this.mCtx = mCtx;
        this.resource = resource;
        this.mList = mlist;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);

        View view = layoutInflater.inflate(resource, null);

        TextView naziv = view.findViewById(R.id.lst_naziv);
        TextView datum = view.findViewById(R.id.lst_vrijeme);
        ImageView slika = view.findViewById(R.id.lst_slika);

        Proizvod proizvod = mList.get(position);

        naziv.setText(proizvod.naziv);

        Calendar cal = Calendar.getInstance();
        Calendar day = Calendar.getInstance();
        try {
            String d = cal.get(Calendar.DAY_OF_MONTH)+"."+(1+cal.get(Calendar.MONTH))+"."+cal.get(Calendar.YEAR);
            cal.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(d));
            day.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(proizvod.datum));
            if(day.after(cal)){
                long msDiff = day.getTimeInMillis() - cal.getTimeInMillis();
                System.out.println(msDiff);
                datum.setText(TimeUnit.MILLISECONDS.toDays(msDiff) +" d");
            }
            else{
                datum.setText("ISTEKLO");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }



        slika.setImageBitmap(BitmapFactory.decodeByteArray(proizvod.slika,0,proizvod.getSlika().length));
        final int id = proizvod.id;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductInfo.class);
                intent.putExtra("id", id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                //((Activity) mCtx).finish();
                //getContext().startActivity(((Activity) mCtx).getIntent());
            }

        });

        return view;
    }
}
