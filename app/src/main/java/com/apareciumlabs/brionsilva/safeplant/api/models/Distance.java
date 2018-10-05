package com.apareciumlabs.brionsilva.safeplant.api.models;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 29/10/2017
 */
public class Distance {
    private Integer id;
    private String date;
    private String time;
    private double distance;

    public Distance(String date, String time, double distance) {
        this.date = date;
        this.time = time;
        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }
}
