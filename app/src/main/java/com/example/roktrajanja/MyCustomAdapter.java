package com.example.roktrajanja;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

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
        datum.setText(proizvod.datum);
        slika.setImageDrawable(mCtx.getDrawable(proizvod.slika));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductInfo.class);
                intent.putExtra("id", position+1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                //((Activity)mCtx).finish();
            }

        });

        return view;
    }
}
