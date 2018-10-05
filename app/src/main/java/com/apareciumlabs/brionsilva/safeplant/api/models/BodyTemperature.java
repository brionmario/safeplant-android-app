package com.apareciumlabs.brionsilva.safeplant.api.models;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 29/10/2017
 */
public class BodyTemperature {

    private Integer id;
    private String date;
    private String time;
    private double temperature;

    public BodyTemperature(String date, String time, double temperature) {
        this.date = date;
        this.time = time;
        this.temperature = temperature;
    }

    public Integer getId() {
        return id;
    }
}
