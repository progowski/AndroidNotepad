package com.example.root.notepad;

import android.content.Context;
import android.os.Build;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by root on 04.05.17.
 */

public class Note implements Serializable{
    private long dateTime;
    private String title;
    private String content;

    public Note(long dateTime, String title, String content) {
        this.dateTime = dateTime;
        this.title = title;
        this.content = content;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDateTime() {
        return dateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDateTimeFormatted(Context context) {
        Locale locale;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(dateTime));
    }
}
