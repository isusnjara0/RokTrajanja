package com.example.roktrajanja;


import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PomKalendar {
    String mdate, mtime;
    int mdays;

    PomKalendar(String date, String time, int days){
        this.mdate = date;
        this.mtime = time;
        this.mdays = days;
    }

    public Calendar getCalendar() {
        SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        SimpleDateFormat time = new SimpleDateFormat("HH:mm",Locale.getDefault());
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(date.parse(mdate));
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);
            c.setTime(time.parse(mtime));
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            c.set(year,month,day,hour,minute);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c;
    }
    public Calendar getCalendarMinusDays(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCalendar().getTime());
        cal.add(Calendar.DATE,-mdays);
        return cal;
    }
}
