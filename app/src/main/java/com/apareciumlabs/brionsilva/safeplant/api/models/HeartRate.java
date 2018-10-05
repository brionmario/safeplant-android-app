package com.apareciumlabs.brionsilva.safeplant.api.models;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 29/10/2017
 */
public class HeartRate {

    private Integer id;
    private String date;
    private String time;
    private int heart_rate;

    public HeartRate(String date, String time, int heart_rate) {
        this.date = date;
        this.time = time;
        this.heart_rate = heart_rate;
    }

    public Integer getId() {
        return id;
    }
}
