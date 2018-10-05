package com.apareciumlabs.brionsilva.safeplant.api.models;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 29/10/2017
 */
public class BloodPressure {

    private Integer id;
    private String date;
    private String time;
    private int diastolic;
    private int systolic;

    public BloodPressure(String date, String time, int diastolic, int systolic) {
        this.date = date;
        this.time = time;
        this.diastolic = diastolic;
        this.systolic = systolic;
    }

    public Integer getId() {
        return id;
    }
}
