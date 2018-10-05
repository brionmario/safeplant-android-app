package com.apareciumlabs.brionsilva.safeplant.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 29/10/2017
 */
public class DateTime {
    private String date;
    private String time;
    SimpleDateFormat sdf;

    public DateTime() {
        findCurrentDate();
        findCurrentTime();
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private void findCurrentDate() {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        setDate(sdf.format(new Date()));
    }

    private void findCurrentTime() {
        Date time = Calendar.getInstance().getTime();
        sdf = new SimpleDateFormat("h:mm a");
        setTime(sdf.format(time));
    }
}
