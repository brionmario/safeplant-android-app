package com.apareciumlabs.brionsilva.safeplant.api.models;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 29/10/2017
 */
public class Calories {
    private Integer id;
    private String date;
    private String time;
    private double calorie_count;

    public Calories(String date, String time, double calorie_count) {
        this.date = date;
        this.time = time;
        this.calorie_count = calorie_count;
    }

    public Integer getId() {
        return id;
    }
}
