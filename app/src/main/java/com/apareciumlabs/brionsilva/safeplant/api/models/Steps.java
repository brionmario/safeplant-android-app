package com.apareciumlabs.brionsilva.safeplant.api.models;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 29/10/2017
 */
public class Steps {

    private Integer id;
    private String date;
    private String time;
    private int step_count;

    public Steps(String date, String time, int step_count) {
        this.date = date;
        this.time = time;
        this.step_count = step_count;
    }

    public Integer getId() {
        return id;
    }
}
