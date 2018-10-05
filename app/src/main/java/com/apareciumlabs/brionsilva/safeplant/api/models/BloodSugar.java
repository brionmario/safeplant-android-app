package com.apareciumlabs.brionsilva.safeplant.api.models;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 29/10/2017
 */
public class BloodSugar {

    private Integer id;
    private String date;
    private String time;
    private double fasting_bs;

    public BloodSugar(String date, String time, double fasting_bs) {
        this.date = date;
        this.time = time;
        this.fasting_bs = fasting_bs;
    }

    public Integer getId() {
        return id;
    }

}
